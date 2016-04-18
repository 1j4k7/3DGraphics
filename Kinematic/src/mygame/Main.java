package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.system.AppSettings;
import com.jme3.util.TangentBinormalGenerator;

/**
 * Yes
 * @author Jason Kuo
 */
public class Main extends SimpleApplication implements PhysicsCollisionListener, PhysicsTickListener {

    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private RigidBodyControl ballPhy;

    private Material brickMat, stoneMat, woodMat;
    private Geometry platformGeo;

    private static final String ELEVATOR = "Elevator";
    private static final float TOPFLOOR = 6f;
    private static final String BALL = "Ball";
    private boolean isPlatformOnTop = false;
    private boolean isBallOnPlatform = false;

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
        flyCam.setMoveSpeed(10f);

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-.5f, -5f, -.5f));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        AmbientLight amb = new AmbientLight();
        rootNode.addLight(amb);

        brickMat = assetManager.loadMaterial("Materials/brick.j3m");
        stoneMat = assetManager.loadMaterial("Materials/pebbles.j3m");
        woodMat = assetManager.loadMaterial("Materials/bark.j3m");

        Node sceneNode = new Node("Scene");

        Box floorMesh = new Box(10f, 0.5f, 10f);
        Geometry floorGeo = new Geometry("Floor", floorMesh);
        floorGeo.setMaterial(stoneMat);
        floorGeo.move(0, -.1f, 0);
        sceneNode.attachChild(floorGeo);

        Box slopeMesh = new Box(6f, 0.1f, 5f);
        Geometry slopeGeo = new Geometry("Slope", slopeMesh);
        slopeGeo.setMaterial(brickMat);
        slopeGeo.rotate(0, 0, FastMath.DEG_TO_RAD * 50);
        slopeGeo.move(4f, 4f, 0);
        sceneNode.attachChild(slopeGeo);

        Box wallMesh = new Box(5f, 0.4f, 5f);
        Geometry wallGeo = new Geometry("Wall", wallMesh);
        wallGeo.setMaterial(brickMat);
        wallGeo.rotate(0, 0, FastMath.DEG_TO_RAD * 90);
        wallGeo.move(-3.5f, 2, 0);
        sceneNode.attachChild(wallGeo);

        scenePhy = new RigidBodyControl(0.0f);
        sceneNode.addControl(scenePhy);
        bulletAppState.getPhysicsSpace().add(scenePhy);
        rootNode.attachChild(sceneNode);

        Box platformMesh = new Box(2f, 0.5f, 5f);
        platformGeo = new Geometry(ELEVATOR, platformMesh);
        platformGeo.setMaterial(woodMat);
        platformGeo.move(-1, 0, 0);
        rootNode.attachChild(platformGeo);
        RigidBodyControl platformPhy = new RigidBodyControl(100.0f);
        platformGeo.addControl(platformPhy);
        platformPhy.setKinematic(true);
        bulletAppState.getPhysicsSpace().add(platformPhy);

        dropBall();
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
        bulletAppState.getPhysicsSpace().addTickListener(this);
    }

    public void dropBall() {
        Sphere ballMesh = new Sphere(32, 32, .75f, true, false);
        ballMesh.setTextureMode(TextureMode.Projected);
        TangentBinormalGenerator.generate(ballMesh);
        Geometry ballGeo = new Geometry(BALL, ballMesh);
        ballGeo.setMaterial(stoneMat);
        rootNode.attachChild(ballGeo);
        ballPhy = new RigidBodyControl(5f);
        ballGeo.addControl(ballPhy);
        bulletAppState.getPhysicsSpace().add(ballPhy);
        ballPhy.setPhysicsLocation(new Vector3f(0, 10, 0));
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (isBallOnPlatform && platformGeo.getLocalTranslation().getY() < TOPFLOOR)
            platformGeo.move(0f, tpf, 0f);
        if (isBallOnPlatform && platformGeo.getLocalTranslation().getY() >= TOPFLOOR)
            isPlatformOnTop = true;
        if (!isBallOnPlatform && platformGeo.getLocalTranslation().getY() > .5f) {
            isPlatformOnTop = false;
            platformGeo.move(0f, -tpf * 4, 0f);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void collision(PhysicsCollisionEvent event) {
        if ((event.getNodeA().getName().equals(BALL) && event.getNodeB().getName().equals(ELEVATOR)) || (event.getNodeA().getName().equals(ELEVATOR) && event.getNodeB().getName().equals(BALL)))
            isBallOnPlatform = true;
        else
            isBallOnPlatform = false;
    }

    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        if (isBallOnPlatform && isPlatformOnTop)
            ballPhy.applyImpulse(new Vector3f(2, 0, 0), Vector3f.ZERO);
    }

    public void physicsTick(PhysicsSpace space, float tpf) {}
}
