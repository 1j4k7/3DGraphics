package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * Rotates when the left mouse button is pressed.
 * Changes color when the space bar is pressed.
 * 2/07/16
 * @author Jason Kuo
 */
public class UserInput extends SimpleApplication {

    private static final Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    private static final Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);

    private static final String MAPPING_COLOR = "Toggle Color";
    private static final String MAPPING_ROTATE = "Rotate";

    private Geometry geom;

    public static void main(String[] args) {
        UserInput app = new UserInput();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR);
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);

        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);

        ActionListener colorListener = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals(MAPPING_COLOR) && !isPressed) {
                    geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
                }
            }
        };
        AnalogListener rotateListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                if (name.equals(MAPPING_ROTATE)) {
                    geom.rotate(0, value, 0);
                }
            }
        };
        inputManager.addListener(colorListener, MAPPING_COLOR);
        inputManager.addListener(rotateListener, MAPPING_ROTATE);
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
