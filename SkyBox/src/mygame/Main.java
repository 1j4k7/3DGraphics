package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * Yes
 * @author Jason Kuo
 */
public class Main extends SimpleApplication {

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
        Texture west = assetManager.loadTexture("Textures/lagoon_west.jpg");
        Texture east = assetManager.loadTexture("Textures/lagoon_east.jpg");
        Texture north = assetManager.loadTexture("Textures/lagoon_north.jpg");
        Texture south = assetManager.loadTexture("Textures/lagoon_south.jpg");
        Texture up = assetManager.loadTexture("Textures/lagoon_up.jpg");
        Texture down = assetManager.loadTexture("Textures/lagoon_down.jpg");
        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);
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
