package min3d.core;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;

import min3d.interfaces.ISceneController;
import min3d.Shared;

/**
 * Extend this class when creating your min3d-based Activity.
 * Then, override initScene() and updateScene() for your main
 * 3D logic.
 *
 * Override onCreateSetContentView() to change layout, if desired.
 *
 * To update 3d scene-related variables from within the the main UI thread,
 * override onUpdateScene() and onUpdateScene() as needed.
 */
public class RendererView extends GLSurfaceView implements ISceneController
{
    private Context context;

    public RendererView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public RendererView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        _initSceneHander = new Handler();
        _updateSceneHander = new Handler();

        //
        // These 4 lines are important.
        //
        Shared.context(context);
        scene = new Scene(this);
        min3d.core.Renderer r = new min3d.core.Renderer(scene);
        Shared.renderer(r);

        glSurfaceViewConfig();
        setRenderer(r);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        //onCreateSetContentView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }




    public Scene scene;
    protected GLSurfaceView _glSurfaceView;

    protected Handler _initSceneHander;
    protected Handler _updateSceneHander;

    private boolean _renderContinuously;


    final Runnable _initSceneRunnable = new Runnable()
    {
        public void run() {
            onInitScene();
        }
    };

    final Runnable _updateSceneRunnable = new Runnable()
    {
        public void run() {
            onUpdateScene();
        }
    };

    /**
     * Any GlSurfaceView settings that needs to be executed before
     * GLSurfaceView.setRenderer() can be done by overriding this method.
     * A couple examples are included in comments below.
     */
    protected void glSurfaceViewConfig()
    {
        // Example which makes glSurfaceView transparent (along with setting scene.backgroundColor to 0x0)
        // _glSurfaceView.setEGLConfigChooser(8,8,8,8, 16, 0);
        // _glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        // Example of enabling logging of GL operations
        // _glSurfaceView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR | GLSurfaceView.DEBUG_LOG_GL_CALLS);
    }

    /**
     * Separated out for easier overriding...
     protected void onCreateSetContentView()
     {
     setContentView(_glSurfaceView);
     }
     */

    /**
     * Instantiation of Object3D's, setting their properties, and adding Object3D's
     * to the scene should be done here. Or any point thereafter.
     *
     * Note that this method is always called after GLCanvas is created, which occurs
     * not only on Activity.onCreate(), but on Activity.onResume() as well.
     * It is the user's responsibility to build the logic to restore state on-resume.
     */
    public void initScene()
    {
    }

    /**
     * All manipulation of scene and Object3D instance properties should go here.
     * Gets called on every frame, right before rendering.
     */
    public void updateScene()
    {
    }

    /**
     * Called _after_ scene init (ie, after initScene).
     * Unlike initScene(), gets called from the UI thread.
     */
    public void onInitScene()
    {
    }

    /**
     * Called _after_ updateScene()
     * Unlike initScene(), gets called from the UI thread.
     */
    public void onUpdateScene()
    {
    }

    /**
     * Setting this to false stops the render loop, and initScene() and onInitScene() will no longer fire.
     * Setting this to true resumes it.
     */
    public void renderContinuously(boolean $b)
    {
        _renderContinuously = $b;
        if (_renderContinuously)
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        else
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public Handler getInitSceneHandler()
    {
        return _initSceneHander;
    }

    public Handler getUpdateSceneHandler()
    {
        return _updateSceneHander;
    }

    public Runnable getInitSceneRunnable()
    {
        return _initSceneRunnable;
    }

    public Runnable getUpdateSceneRunnable()
    {
        return _updateSceneRunnable;
    }
}
