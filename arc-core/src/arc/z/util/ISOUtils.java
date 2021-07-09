package arc.z.util;

import arc.math.geom.Point2;
import arc.math.geom.Position;
import arc.math.geom.Vec2;

/**
 *
 */
public class ISOUtils {
    private static final Vec2 v1 = new Vec2();
    private static final Vec2 v2 = new Vec2();
    private static final float INTEGER_HALF = 0.5f;

    // 权倾天下游戏Tile尺寸
    public static int WIDTH = 78;
    public static int HEIGHT = 60;
    public static float TILE_WIDTH    = 78;        // default 79;    78
    public static float TILE_HEIGHT   = 60;        // default 61;   60
    public static float TILE_WIDTH50  = 78  / 2f;// >> 1;
    public static float TILE_HEIGHT50 = 60 / 2f;// >> 1;

    private static final int CHUNK = 32;
    public static float CHUNK_WIDTH    = 78 * CHUNK;        // default 79;    78
    public static float CHUNK_HEIGHT   = 60 * CHUNK;        // default 61;   60
    public static float CHUNK_WIDTH50  = 78  / 2f * CHUNK;// >> 1;
    public static float CHUNK_HEIGHT50 = 60 / 2f * CHUNK;// >> 1;

    static {
        TILE_WIDTH *= 0.25f;
        TILE_HEIGHT *= 0.25f;
        TILE_WIDTH50 *= 0.25f;
        TILE_HEIGHT50 *= 0.25f;
        CHUNK_WIDTH *= 0.25f;
        CHUNK_HEIGHT *= 0.25f;
        CHUNK_WIDTH50 *= 0.25f;
        CHUNK_HEIGHT50 *= 0.25f;
    }

    private ISOUtils() {}

    /**
     * 重置地图瓦砾尺寸
     * @param scale 绘制的缩放比例.default value 1
     * */
    public static void resize(int tilewidth, int tileheight, float scale) {
        TILE_WIDTH = tilewidth * scale;
        TILE_HEIGHT = tileheight * scale;
        TILE_WIDTH50  = (tilewidth  / 2f) * scale;// >> 1;
        TILE_HEIGHT50 = (tileheight / 2f) * scale;// >> 1;
        CHUNK_WIDTH = tilewidth * scale * CHUNK;
        CHUNK_HEIGHT = tileheight * scale * CHUNK;
        CHUNK_WIDTH50  = (tilewidth  / 2f) * scale * CHUNK;// >> 1;
        CHUNK_HEIGHT50 = (tileheight / 2f) * scale * CHUNK;// >> 1;
    }

    // 瓦砾坐标转化为世界坐标 begon

    public static float tileToWorldX(float tx, float ty) {
        return (tx + ty) * TILE_WIDTH50;
    }
    public static float tileToWorldY(float tx, float ty) {
        return (tx - ty) * TILE_HEIGHT50;
    }

    public static Vec2 tileToWorldCoords(Vec2 tile, Vec2 dst) {
        return tileToWorldCoords(tile.x, tile.y, dst);
    }

    // temp begon
    public static Vec2 tileToWorldCoordsCenter(float tx, float ty, float tw, float th, Vec2 dst) {
        int offsetx = -( (int)tw - 1) / 2;
        int offsety = -( (int)th - 1) / 2;
        tx += tw / 2f - INTEGER_HALF + offsetx;
        ty += th / 2f - INTEGER_HALF + offsety;
        dst.x = +(tx + ty) * TILE_WIDTH50;
        dst.y = +(tx - ty) * TILE_HEIGHT50;    // +y轴向上延申,-y轴向上延申
        return dst;
    }
    // temp end

    /** 禁止多线程使用*/
    public static Vec2 tileToWorldCoords(float tx, float ty) {
        v1.x = +(tx + ty) * TILE_WIDTH50;
        v1.y = +(tx - ty) * TILE_HEIGHT50;    // +y轴向上延申,-y轴向上延申
        return v1;
    }

    /** 禁止多线程使用*/
    public static Vec2 tileToWorldCoords(Position tile) {
        v1.x = +(tile.getX() + tile.getY()) * TILE_WIDTH50;
        v1.y = +(tile.getX() - tile.getY()) * TILE_HEIGHT50;    // +y轴向上延申,-y轴向上延申
        return v1;
    }

    public static Vec2 tileToWorldCoords(float tx, float ty, Vec2 dst) {
        dst.x = +(tx + ty) * TILE_WIDTH50;
        dst.y = +(tx - ty) * TILE_HEIGHT50;    // +y轴向上延申,-y轴向上延申
        return dst;
    }

    public static Point2 tileToWorldCoords(Point2 world, Point2 dst) {
        return tileToWorldCoords(world.x, world.y, dst);
    }

    public static Point2 tileToWorldCoords(int tx, int ty, Point2 dst) {
        dst.x = (int) (+(tx + ty) * TILE_WIDTH50);
        dst.y = (int) (+(tx - ty) * TILE_HEIGHT50);   // +y轴向上延申,-y轴向上延申
        return dst;
    }

    public static Vec2 tileToWorldCoords(float tx, float ty, float tileWidth, float tileHeight, Vec2 dst) {
        dst.x = +(tx + ty) * (tileWidth / 2);
        dst.y = +(tx - ty) * (tileHeight / 2);    // +y轴向上延申,-y轴向上延申
        return dst;
    }
    // 瓦砾坐标转化为世界坐标 end

    // 世界坐标转化为瓦砾坐标 begon

    public static float worldToTileCenterX(float wx, float wy) {
        float value = worldToTileX(wx, wy);
        return value > 0 ? value + INTEGER_HALF : value - INTEGER_HALF;
//    return worldToTileX(wx - 0, wy - 0);
    }
    public static float worldToTileCenterY(float wx, float wy) {
        float value = worldToTileY(wx, wy);
        return value > 0 ? value + INTEGER_HALF : value - INTEGER_HALF;
//    return worldToTileY(wx - 0, wy - 0);
    }

    public static float worldToTileX(float wx, float wy) {
        return INTEGER_HALF * (wy / TILE_HEIGHT50 + wx / TILE_WIDTH50);
    }
    public static float worldToTileY(float wx, float wy) {
        return INTEGER_HALF * (-wy / TILE_HEIGHT50 + wx / TILE_WIDTH50);
    }

    public static Vec2 worldToTileCoords(Vec2 tile, Vec2 dst) {
        return worldToTileCoords(tile.x, tile.y, dst);
    }

    public static Vec2 worldToTileCoords(Vec2 wpos) {
        v1.x = INTEGER_HALF * (wpos.y / TILE_HEIGHT50 + wpos.x / TILE_WIDTH50);
        v1.y = INTEGER_HALF * (-wpos.y / TILE_HEIGHT50 + wpos.x / TILE_WIDTH50);
        return v1;
    }

    public static Vec2 worldToTileCoords(float wposx, float wposy) {
        v1.x = INTEGER_HALF * (wposy / TILE_HEIGHT50 + wposx / TILE_WIDTH50);
        v1.y = INTEGER_HALF * (-wposy / TILE_HEIGHT50 + wposx / TILE_WIDTH50);
        return v1;
    }

    public static Vec2 worldToTileCoords(float wx, float wy, Vec2 dst) {
        dst.x = INTEGER_HALF * (wy / TILE_HEIGHT50 + wx / TILE_WIDTH50);
        dst.y = INTEGER_HALF * (-wy / TILE_HEIGHT50 + wx / TILE_WIDTH50);
        return dst;
    }

    public static Point2 worldToTileCoords(Point2 world, Point2 dst) {
        return worldToTileCoords(world.x, world.y, dst);
    }

    public static Point2 worldToTileCoords(int wx, int wy, Point2 dst) {
        dst.x = (int) (INTEGER_HALF * (wy / TILE_HEIGHT50 + wx / TILE_WIDTH50));
        dst.y = (int) (INTEGER_HALF * (-wy / TILE_HEIGHT50 + wx / TILE_WIDTH50));
        return dst;
    }
    // 世界坐标转化为瓦砾坐标 end


    /** 世界坐标转缓存坐标*/
    public static Vec2 worldToChunkCoords(float wposx, float wposy) {
        v2.x = INTEGER_HALF * (wposy / CHUNK_HEIGHT50 + wposx / CHUNK_WIDTH50);
        v2.y = INTEGER_HALF * (-wposy / CHUNK_HEIGHT50 + wposx / CHUNK_WIDTH50);
        return v2;
    }

    public static Vec2 worldToChunkCoords(Vec2 wpos) {
        return worldToChunkCoords(wpos.x, wpos.y);
    }

    public static float worldToChunkX(float wx, float wy) {
        return INTEGER_HALF * (wy / CHUNK_HEIGHT50 + wx / CHUNK_WIDTH50);
    }
    public static float worldToChunkY(float wx, float wy) {
        return INTEGER_HALF * (-wy / CHUNK_HEIGHT50 + wx / CHUNK_WIDTH50);
    }
}
