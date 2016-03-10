package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
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
        flyCam.setMoveSpeed(5f);

        Node tank = (Node) assetManager.loadModel("Models/Tank.j3o");
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        TextureKey tankDiffuse = new TextureKey("Models/tank_diffuse.jpg", false);
        mat.setTexture("DiffuseMap", assetManager.loadTexture(tankDiffuse));
        TangentBinormalGenerator.generate(tank);
        TextureKey tankNormal = new TextureKey("Models/tank_normals.png", false);
        mat.setTexture("NormalMap", assetManager.loadTexture(tankNormal));
        TextureKey tankSpecular = new TextureKey("Models/tank_specular.jpg", false);
        mat.setTexture("SpecularMap", assetManager.loadTexture(tankSpecular));
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Gray);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 100f);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.SceneAndObjects);
        fpp.addFilter(bloom);
        TextureKey tankGlow = new TextureKey("Models/tank_glow_map.jpg", false);
        mat.setTexture("GlowMap", assetManager.loadTexture(tankGlow));
        mat.setColor("GlowColor", ColorRGBA.White);
        tank.setMaterial(mat);
        rootNode.attachChild(tank);

        Box floor = new Box(10f, 10f, 1);
        Geometry floorGeom = new Geometry("Floor", floor);
        floorGeom.rotate((float)Math.PI/2f, 0f, 0f);
        floorGeom.move(0f, -3f, 0f);
        mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        TextureKey floorDiffuse = new TextureKey("Models/BrickWall_diffuse.jpg", false);
        mat.setTexture("DiffuseMap", assetManager.loadTexture(floorDiffuse));
        TangentBinormalGenerator.generate(floorGeom);
        TextureKey floorNormals = new TextureKey("Models/BrickWall_normal.jpg", false);
        mat.setTexture("NormalMap", assetManager.loadTexture(floorNormals));
        TextureKey floorSpecular = new TextureKey("Models/BrickWall_height.jpg", false);
        mat.setTexture("SpecularMap", assetManager.loadTexture(floorSpecular));
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Gray);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 1000f);
        floorGeom.setMaterial(mat);
        rootNode.attachChild(floorGeom);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-.5f, -5f, -.5f));
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
