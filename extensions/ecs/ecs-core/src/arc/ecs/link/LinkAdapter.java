package arc.ecs.link;

/**
 * Stub implementation of {@link LinkListener}.
 * @see EntityLinkManager#register(Class, LinkListener)
 * @see EntityLinkManager#register(Class, String, LinkListener)
 */
public class LinkAdapter implements LinkListener{
    @Override
    public void onLinkEstablished(int sourceId, int targetId){
    }

    @Override
    public void onLinkKilled(int sourceId, int targetId){
    }

    @Override
    public void onTargetDead(int sourceId, int deadTargetId){
    }

    @Override
    public void onTargetChanged(int sourceId, int targetId, int oldTargetId){
    }
}
