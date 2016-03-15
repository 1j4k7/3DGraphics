package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
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

        Node monkey = (Node) assetManager.loadModel("Models/Jaime.j3o");
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
        mat.setFloat("Shininess", 100f);
        monkey.setMaterial(mat);
        rootNode.attachChild(monkey);

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
