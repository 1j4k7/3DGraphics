package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * yes
 * @author Jason Kuo
 */
public class Main extends SimpleApplication {

    private Node sceneNode;
    private Node playerNode;
    private CameraNode camNode;

    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private BetterCharacterControl playerControl;

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

        assetManager.registerLocator("town.zip", ZipLocator.class);
        sceneNode = (Node) assetManager.loadModel("main.scene");
        sceneNode.scale(1.5f);
        rootNode.attachChild(sceneNode);

        playerNode = new Node("the player");
        playerNode.setLocalTranslation(new Vector3f(0, 6, 0));
        rootNode.attachChild(playerNode);
        playerControl = new BetterCharacterControl(1.5f, 4f, 30f);
        playerControl.setJumpForce(new Vector3f(0, 300, 0));
        playerControl.setGravity(new Vector3f(0, -9.81f, 0));
        playerNode.addControl(playerControl);


        AmbientLight ambient = new AmbientLight();
        rootNode.addLight(ambient);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1.4f, -1.4f, -1.4f));
        rootNode.addLight(sun);

        viewPort.setBackgroundColor(ColorRGBA.Cyan);

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        scenePhy = new RigidBodyControl(0f);
        sceneNode.addControl(scenePhy);
        bulletAppState.getPhysicsSpace().add(playerControl);
        bulletAppState.getPhysicsSpace().add(sceneNode);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
    }

    @Override
    public void simpleUpdate(float tpf) {
        camNode = new CameraNode("CamNode", cam);
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
