package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * There is a red and a blue box. Left click to rotate and space bar to change
 * the color of whichever box you choose.
 * 2/07/16
 * @author Jason Kuo
 */
public class TargetPickCenter extends SimpleApplication {

    private static final Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    private static final Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);

    private static final String MAPPING_COLOR = "Toggle Color";
    private static final String MAPPING_ROTATE = "Rotate";

    private static Box mesh = new Box(1, 1, 1);

    public static void main(String[] args) {
        TargetPickCenter app = new TargetPickCenter();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR);
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);

        rootNode.attachChild(createBox("Red Cube", new Vector3f(0, 1.5f, 0), ColorRGBA.Red));
        rootNode.attachChild(createBox("Blue Cube", new Vector3f(0, -1.5f, 0), ColorRGBA.Blue));

        Geometry c = createBox("Center", Vector3f.ZERO, ColorRGBA.White);
        c.scale(4);
        c.setLocalTranslation(settings.getWidth()/2, settings.getHeight()/2, 0);
        guiNode.attachChild(c);

        ActionListener colorListener = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals(MAPPING_COLOR) && !isPressed) {
                    CollisionResults results = new CollisionResults();
                    Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                    rootNode.collideWith(ray, results);
                    if (results.size() > 0) {
                        Geometry target = results.getClosestCollision().getGeometry();
                        target.getMaterial().setColor("Color", ColorRGBA.randomColor());
                    } else {
                        System.out.println("You suck at pointing");
                    }
                }
            }
        };
        AnalogListener rotateListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                if (name.equals(MAPPING_ROTATE)) {
                    CollisionResults results = new CollisionResults();
                    Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                    rootNode.collideWith(ray, results);
                    if (results.size() > 0) {
                        Geometry target = results.getClosestCollision().getGeometry();
                        if (target.getName().equals("Red Cube")) {
                            target.rotate(0, -value, 0);
                        } else if (target.getName().equals("Blue Cube")) {
                            target.rotate(0, value, 0);
                        }
                    } else {
                        System.out.println("You suck at pointing");
                    }
                }
            }
        };
        inputManager.addListener(colorListener, MAPPING_COLOR);
        inputManager.addListener(rotateListener, MAPPING_ROTATE);
    }

    public Geometry createBox(String name, Vector3f loc, ColorRGBA color) {
        Geometry geom = new Geometry(name, mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
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
