package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.TangentBinormalGenerator;

/**
 * test
 * @author normenhansen
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
        flyCam.setMoveSpeed(100f);

        Material terrainMat = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        Texture grass = assetManager.loadTexture("Textures/grass.png");
        grass.setWrap(WrapMode.Repeat);
        terrainMat.setTexture("Tex1", grass);
        terrainMat.setFloat("Tex1Scale", 128);
        terrainMat.setTexture("Tex2", grass);
        terrainMat.setFloat("Tex2Scale", 128);
        terrainMat.setTexture("Tex3", grass);
        terrainMat.setFloat("Tex3Scale", 128);
        terrainMat.setBoolean("useTriPlanarMapping", true);
        AbstractHeightMap heightMap = null;
        try {
            heightMap = new HillHeightMap(1025, 1000, 50, 100, (byte)3);
            heightMap.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TerrainQuad terrain = new TerrainQuad("terrain", 65, 513, heightMap.getHeightMap());

        terrain.setMaterial(terrainMat);
        rootNode.attachChild(terrain);

        Spatial treeGeo = assetManager.loadModel("Models/Tree/Tree.j3o");
        treeGeo.scale(5);
        treeGeo.setQueueBucket(RenderQueue.Bucket.Transparent);
        rootNode.attachChild(treeGeo);
        Vector3f treeLoc = new Vector3f(0, 0, -30);
        treeLoc.setY(terrain.getHeight(new Vector2f(treeLoc.x, treeLoc.z)));
        treeGeo.setLocalTranslation(treeLoc);

        AmbientLight ambient = new AmbientLight();
        rootNode.addLight(ambient);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1.4f, -1.4f, -1.4f));
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
