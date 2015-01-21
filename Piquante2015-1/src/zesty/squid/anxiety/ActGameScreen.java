package zesty.squid.anxiety;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
 
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

// Tower of Hanoi tutorial: http://www.raywenderlich.com/12065/how-to-create-a-simple-android-game

// Board game forum post: http://www.andengine.org/forums/gles1/help-with-boardgame-t1388.html

public class ActGameScreen extends SimpleBaseGameActivity implements IOnSceneTouchListener{

	protected static int CAMERA_WIDTH = 480;
	protected static int CAMERA_HEIGHT = 800;
	protected static int NUM_ROWS = 11;
	protected static int NUM_COLS = 7;
	protected static int SQUARE_SIDE = 61;
	
	protected static int BOARD_MIN_X = 27;
	protected static int BOARD_MIN_Y = 104;
	protected static int BOARD_MAX_X = 452;
	protected static int BOARD_MAX_Y = 773;
	
	// Q1  Q2
	//
	// Q3  Q4
	protected static SeatBoundary q1Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,287);
	protected static SeatBoundary q2Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,287);
	protected static SeatBoundary q3Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,592);
	protected static SeatBoundary q4Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,592);
	
	private ITextureRegion mBackgroundTextureRegion, mBlobTextureRegion, mSeatsTextureRegion;
	private Sprite blob;
	private Sprite seats;
	
	private GridInfo gridInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gridInfo = new GridInfo(); //BOARD_MIN_X, BOARD_MIN_Y, BOARD_MAX_X, BOARD_MAX_Y, SQUARE_SIDE);
		Log.w("GRID_DEBUG", "GRID_DEBUG: " + gridInfo.debugString());
		//setContentView(R.layout.act_game_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_game_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		
		/*
		 The parameters that are passed while creating an instance of EngineOptions are:

    FullScreen: A boolean variable signifying whether or not the engine instance will use a fullscreen.
    ScreenOrientation: Specifies the orientation used while the game is running.
    ResolutionPolicy: Defines how the engine will scale the game assets on phones with different screen sizes.
    Camera: Defines the width and height of the final game scene.

		 */
	}

	@Override
	protected void onCreateResources() {
		try {
		    // 1 - Set up bitmap textures
		    ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/baseBackground.png");
		        }
		    });
		    ITexture blobTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/dood60.png");
		        }
		    });
		    ITexture seatsSouthTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/seats.png");
		        }
		    });
		    
		    
		    // 2 - Load bitmap textures into VRAM
		    backgroundTexture.load();
		    blobTexture.load();
		    seatsSouthTexture.load();
		    
		    // 3 - Set up texture regions
		    this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		    this.mBlobTextureRegion = TextureRegionFactory.extractFromTexture(blobTexture);
		    this.mSeatsTextureRegion = TextureRegionFactory.extractFromTexture(seatsSouthTexture);
		    
		} catch (IOException e) {
		    Debug.e(e);
		}
		/* From Tower of Hanoi:
		 * Note: Instead of creating textures for each of your assets, you could have loaded 
		 * all the assets into one texture and extracted the individual assets as TextureRegions. 
		 * Doing this is out of scope for this tutorial, but I may cover it in detail in a future tutorial.
		 */
	}

	@Override
	protected Scene onCreateScene() {
		// 1 - Create new scene
		final Scene scene = new Scene(){
			public boolean onTouchEvent(MotionEvent event) {
		        
				return true;
		    }
		};
		
		Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		
		/* From Tower of Hanoi:
		 * When creating a Sprite object, you pass four parameters. Here’s a brief description of each parameter:

		    xCoordinate: Defines the X-position of the sprite. The AndEngine coordinate system considers the top-left point as the origin.
		    yCoordinate: Defines the Y-position of the sprite.
		    TextureRegion: Defines what part of the texture the sprite will use to draw itself.
		    VertexBufferObjectManager: Think of a vertex buffer as an array holding the coordinates of a texture. 
		   		These coordinates are passed to the OpenGL ES pipeline and ultimately define what will be drawn. 
		   		A VertexBufferObjectManager holds all the vertices of all the textures that need to be drawn on the scene.

		 */
		
		// 2 - Add the person and seats
		seats = new AllSeats(BOARD_MIN_X, BOARD_MIN_Y, this.mSeatsTextureRegion, getVertexBufferObjectManager());
		blob = new PlayerSprite(BOARD_MIN_X, BOARD_MIN_Y + SQUARE_SIDE*5, this.mBlobTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(seats);
		scene.attachChild(blob);
		
		// 3. Register swipe listeners
		// Working with touch on the entire scene: https://andengineguides.wordpress.com/2011/09/14/getting-started-touch-events/
		scene.setOnSceneTouchListener(this);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		// Set up seat boundaries
		/*
		q1Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,286);
        q2Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,286);
        q3Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,591);
        q4Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,591);
		*/
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		return blob.onAreaTouched(pSceneTouchEvent, pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
	}
	
}
