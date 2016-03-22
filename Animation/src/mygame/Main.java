package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.util.TangentBinormalGenerator;

/**
 * yes
 * @author Jason Kuo
 */
public class Main extends SimpleApplication {

    private Node monkey;

    private AnimControl control;
    private AnimChannel channel;
    private static final String ANIMATION_IDLE = "Idle";
    private static final String ANIMATION_WALK = "Walk";

    private static final String MAPPING_WALK = "walk forward";
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(MAPPING_WALK) && isPressed) {
                if (!channel.getAnimationName().equals(ANIMATION_WALK)) {
                    channel.setAnim(ANIMATION_WALK);
                }
            }
            if (name.equals(MAPPING_WALK) && !isPressed) {
                    channel.setAnim(ANIMATION_IDLE);
                }
        }

    };
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals(MAPPING_WALK))
                monkey.move(0, 0, tpf);
        }

    };

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
        flyCam.setMoveSpeed(5f);

        monkey = (Node) assetManager.loadModel("Models/Jaime.j3o");
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        TextureKey diffuse = new TextureKey("Models/diffuseMap.jpg", false);
        mat.setTexture("DiffuseMap", assetManager.loadTexture(diffuse));
        TangentBinormalGenerator.generate(monkey);
        TextureKey normal = new TextureKey("Models/NormalMap.png", false);
        mat.setTexture("NormalMap", assetManager.loadTexture(normal));
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Gray);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        monkey.setMaterial(mat);
        rootNode.attachChild(monkey);
        monkey.rotate(0, (float) Math.PI, 0);

        control = monkey.getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim(ANIMATION_IDLE);

        inputManager.addMapping(MAPPING_WALK, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(analogListener, MAPPING_WALK);
        inputManager.addListener(actionListener, MAPPING_WALK);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
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
