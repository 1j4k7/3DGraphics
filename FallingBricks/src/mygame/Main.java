package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.system.AppSettings;

/**
 * Physics!
 * @author Jason Kuo
 */
public class Main extends SimpleApplication {

    private BulletAppState bulletAppState;

    private Material brickMat, stoneMat, woodMat;
    private static final Sphere ballMesh;
    private static final Box brickMesh;
    private static final Box floorMesh;
    private static Node wallNode;
    private RigidBodyControl brickPhy;
    private RigidBodyControl ballPhy;
    private RigidBodyControl floorPhy;

    private static final float BRICK_LENGTH = 0.4f;
    private static final float BRICK_WIDTH = 0.3f;
    private static final float BRICK_HEIGHT = 0.25f;
    private static final float WALL_WIDTH = 12;
    private static final float WALL_HEIGHT = 6;
    private static final String SHOOT = "shoot";

    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(SHOOT) && !isPressed)
                shootCannonBall();
        }

    };

    static {
            floorMesh = new Box(Vector3f.ZERO, 10f, 0.5f, 5f);
            brickMesh = new Box(Vector3f.ZERO, BRICK_LENGTH, BRICK_HEIGHT, BRICK_WIDTH);
            ballMesh = new Sphere(32, 32, 0.25f, true, false);
            ballMesh.setTextureMode(TextureMode.Projected);
            floorMesh.scaleTextureCoordinates(new Vector2f(4f, 4f));
        }

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024, 600);
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        brickMat = assetManager.loadMaterial("Materials/brick.j3m");
        stoneMat = assetManager.loadMaterial("Materials/pebbles.j3m");
        woodMat = assetManager.loadMaterial("Materials/bark.j3m");

        Geometry floorGeo = new Geometry("Floor", floorMesh);
        floorGeo.setMaterial(woodMat);
        floorGeo.move(0f, -BRICK_HEIGHT*2f, 0f);
        rootNode.attachChild(floorGeo);
        floorPhy = new RigidBodyControl(0f);
        floorGeo.addControl(floorPhy);
        bulletAppState.getPhysicsSpace().add(floorPhy);

        wallNode = new Node("wall");
        float offsetH = BRICK_LENGTH/3;
        float offsetV = 0;
        for (int j = 0; j < WALL_HEIGHT; j++) {
            for (int i = 0; i < WALL_WIDTH; i++) {
                Vector3f brickPos = new Vector3f(offsetH + BRICK_LENGTH*2.1f*i - BRICK_LENGTH*WALL_WIDTH, offsetV + BRICK_HEIGHT, 0f);
                wallNode.attachChild(makeBrick(brickPos));
            }
            offsetH = -offsetH;
            offsetV += 2*BRICK_HEIGHT;
        }
        rootNode.attachChild(wallNode);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-.5f, -5f, -.5f));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        AmbientLight amb = new AmbientLight();
        rootNode.addLight(amb);

        inputManager.addMapping(SHOOT, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, SHOOT);
    }

    public Geometry makeBrick(Vector3f loc) {
        Geometry brickGeo = new Geometry("brick", brickMesh);
        brickGeo.setMaterial(brickMat);
        wallNode.attachChild(brickGeo);
        brickGeo.move(loc);
        brickPhy = new RigidBodyControl(5f);
        brickGeo.addControl(brickPhy);
        bulletAppState.getPhysicsSpace().add(brickPhy);
        return brickGeo;
    }

    public void shootCannonBall() {
        Geometry ballGeo = new Geometry("cannon ball", ballMesh);
        ballGeo.setMaterial(stoneMat);
        ballGeo.setLocalTranslation(cam.getLocation());
        rootNode.attachChild(ballGeo);
        ballPhy = new RigidBodyControl(5f);
        ballGeo.addControl(ballPhy);
        ballPhy.setCcdSweptSphereRadius(0.1f);
        ballPhy.setCcdMotionThreshold(0.001f);
        ballPhy.setLinearVelocity(cam.getDirection().mult(50));
        bulletAppState.getPhysicsSpace().add(ballPhy);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
