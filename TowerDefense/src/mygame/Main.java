package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * A tower defense setting where the player has a top down perspective
 * on the towers (green), a base (yellow), and a creep (black)
 * 2/03/16
 * @author Jason Kuo
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024, 600);
        settings.setSamples(16);
        settings.setTitle("Tower Defense");
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10f);

        Box floor = new Box(10f, 10f, 1);
        Geometry floorGeom = new Geometry("Floor", floor);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Orange);
        floorGeom.move(new Vector3f(0f, 0f, -1f));
        floorGeom.setMaterial(mat);

        rootNode.attachChild(floorGeom);

        Node playerNode = new Node("player base");
        playerNode.attachChild(createBase(new Vector3f(2f, 0f, .5f)));
        rootNode.attachChild(playerNode);

        Node towerNode = new Node("tower");
        towerNode.attachChild(createTower(new Vector3f(0f, 2f, 3f)));
        towerNode.attachChild(createTower(new Vector3f(0f, -2f, 3f)));
        rootNode.attachChild(towerNode);

        Node creepNode = new Node("creep");
        creepNode.attachChild(createCreep(new Vector3f(-5f, 0f, .2f)));
        rootNode.attachChild(creepNode);


    }

    public Geometry createBase(Vector3f pos) {
        Box base = new Box(1f, 1f, .5f);
        Geometry baseGeom = new Geometry("Base", base);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        baseGeom.setMaterial(mat);
        baseGeom.move(pos);
        return baseGeom;
    }

    public Geometry createTower(Vector3f pos) {
        Box tower = new Box(.5f, .5f, 3f);
        Geometry towerGeom = new Geometry("Tower", tower);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        towerGeom.setMaterial(mat);
        towerGeom.move(pos);
        return towerGeom;
    }

    public Geometry createCreep(Vector3f pos) {
        Box creep = new Box(.2f, .2f, .2f);
        Geometry creepGeom = new Geometry("Creep", creep);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        creepGeom.setMaterial(mat);
        creepGeom.move(pos);
        return creepGeom;
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
