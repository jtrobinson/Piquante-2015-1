package zesty.squid.anxiety;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Seat extends Sprite {
	
	public static int FACING_NULL = -1;
	public static int FACING_NORTH = 0;
	public static int FACING_EAST = 1;
	public static int FACING_SOUTH = 2;
	public static int FACING_WEST = 3;

	public Seat(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }
}
