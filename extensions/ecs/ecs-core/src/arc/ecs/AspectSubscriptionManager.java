package arc.ecs;

import arc.ecs.annotations.*;
import arc.ecs.utils.*;

import java.util.*;

import static arc.ecs.Aspect.all;

/**
 * <p>Manages all instances of {@link EntitySubscription}.</p>
 *
 * <p>Entity subscriptions are automatically updated during {@link Base#process()}.
 * Any {@link EntitySubscription.SubscriptionListener listeners}
 * are informed when entities are added or removed.</p>
 * @see EntitySubscription
 */
@SkipWire
public class AspectSubscriptionManager extends BaseSystem{

    private final Map<Aspect.Builder, EntitySubscription> subscriptionMap;
    private final Bag<EntitySubscription> subscriptions = new Bag(EntitySubscription.class);

    private final IntBag changed = new IntBag();
    private final IntBag deleted = new IntBag();

    protected AspectSubscriptionManager(){
        subscriptionMap = new HashMap<>();
    }

    @Override
    protected void processSystem(){
    }

    @Override
    protected void setBase(Base base){
        super.setBase(base);

        // making sure the first subscription matches all entities
        get(all());
    }

    /**
     * <p>Gets the entity subscription for the {@link Aspect}.
     * Subscriptions are only created once per aspect.</p>
     * <p>
     * Be careful when calling this within {@link BaseSystem#processSystem()}.
     * If the subscription does not exist yet, the newly created subscription
     * will reflect all the chances made by the currently processing system,
     * NOT the state before the system started processing. This might cause
     * the system to behave differently when run the first time (as
     * subsequent calls won't have this issue).
     * See https://github.com/junkdog/artemis-odb/issues/551
     * @param builder Aspect to match.
     * @return {@link EntitySubscription} for aspect.
     */
    public EntitySubscription get(Aspect.Builder builder){
        EntitySubscription subscription = subscriptionMap.get(builder);
        return (subscription != null) ? subscription : createSubscription(builder);
    }

    private EntitySubscription createSubscription(Aspect.Builder builder){
        EntitySubscription entitySubscription = new EntitySubscription(base, builder);
        subscriptionMap.put(builder, entitySubscription);
        subscriptions.add(entitySubscription);

        base.getComponentManager().synchronize(entitySubscription);
        return entitySubscription;
    }

    /**
     * Informs all listeners of added, changedBits and deletedBits changes.
     * <p>
     * Order of {@link EntitySubscription.SubscriptionListener} can vary
     * (typically ordinal, except for subscriptions created in process,
     * initialize instead of setWorld).
     * <p>
     * {@link EntitySubscription.SubscriptionListener#inserted(IntBag)}
     * {@link EntitySubscription.SubscriptionListener#removed(IntBag)}
     * @param changedBits Entities with changedBits composition or state.
     * @param deletedBits Entities removed from world.
     */
    void process(BitVector changedBits, BitVector deletedBits){
        toEntityIntBags(changedBits, deletedBits);

        // note: processAll != process
        subscriptions.get(0).processAll(changed, deleted);

        for(int i = 1, s = subscriptions.size(); s > i; i++){
            subscriptions.get(i).process(changed, deleted);
        }
    }

    private void toEntityIntBags(BitVector changed, BitVector deleted){
        changed.toIntBagIdCid(base.getComponentManager(), this.changed);
        deleted.toIntBag(this.deleted);

        changed.clear();
        deleted.clear();
    }

    void processComponentIdentity(int id, BitVector componentBits){
        for(int i = 0, s = subscriptions.size(); s > i; i++){
            subscriptions.get(i).processComponentIdentity(id, componentBits);
        }
    }

    /**
     * Gets the active list of all current entity subscriptions. Meant to assist
     * in tooling/debugging.
     * @return All active subscriptions.
     */
    public ImmutableBag<EntitySubscription> getSubscriptions(){
        return subscriptions;
    }
}
