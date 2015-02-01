package zesty.squid.anxiety;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.widget.Toast;

public class GameScreenV2 extends SimpleBaseGameActivity implements IOnSceneTouchListener{

	protected static int CAMERA_WIDTH = 480;
	protected static int CAMERA_HEIGHT = 800;
	protected static int BOARD_MIN_X = 27;
	protected static int BOARD_MIN_Y = 104;
	protected static int BOARD_MAX_X = 452;
	protected static int BOARD_MAX_Y = 773;
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	//private TiledTextureRegion mPlayerTextureRegion;
	private TMXTiledMap mTMXTiledMap;
	private BoundCamera mCamera;
	
	protected int mSeatCount;
	protected int mCactusCount;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		//this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.DEFAULT);
		//this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "player64.png", 0, 0, 3, 4);

		//this.mBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		// http://www.andengine.org/forums/gles2/need-help-in-adding-tmxmap-t14059-10.html
		// The problem was removing the ../ from the beginning of the tileset file path!!!!!!! (that and gzip encoding)
		// New problem: flickering. But I'm still using 60px tiles; try using 64.
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		try {
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager(), new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					// We are going to count the tiles that have the property "seat=true" set.
					if(pTMXTileProperties.containsTMXProperty("cactus", "true")) {
						GameScreenV2.this.mCactusCount++;
					}
				}
			});
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/subwaygzip.tmx");
			//this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/subwayOverDesert.tmx");

			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(GameScreenV2.this, "What up.", Toast.LENGTH_LONG).show();//GameScreenV2.this, "Seat count in this TMXTiledMap: " + GameScreenV2.this.mSeatCount, Toast.LENGTH_LONG).show();
				}
			});
		
			final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
			scene.attachChild(tmxLayer);
		
			/* Make the camera not exceed the bounds of the TMXEntity. */
			this.mCamera.setBounds(0, 0, tmxLayer.getHeight(), tmxLayer.getWidth());
			this.mCamera.setBoundsEnabled(true);
		} catch (final TMXLoadException e) {
			Debug.e("THIRD TRY DEBUG: " + e);
		}
		
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}

}
