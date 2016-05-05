package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * Yes
 * @author Jason Kuo
 */
public class Main extends SimpleApplication {

    private static final Trigger TRIGGER_EXPLODE = new KeyTrigger(KeyInput.KEY_SPACE);
    private static final String MAPPING_EXPLODE = "Explode";

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
        ParticleEmitter sparksEmitter = new ParticleEmitter("Spark Emitter", Type.Triangle, 100);
        rootNode.attachChild(sparksEmitter);
        Material sparkMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        sparkMat.setTexture("Texture", assetManager.loadTexture("Textures/spark.png"));
        sparksEmitter.setMaterial(sparkMat);
        sparksEmitter.setImagesX(1);
        sparksEmitter.setImagesY(1);
        sparksEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 10, 0));
        sparksEmitter.getParticleInfluencer().setVelocityVariation(1);
        sparksEmitter.setStartColor(ColorRGBA.Yellow);
        sparksEmitter.setEndColor(ColorRGBA.Red);
        sparksEmitter.setGravity(0, 50, 0);
        sparksEmitter.setFacingVelocity(true);
        sparksEmitter.setStartSize(.3f);
        sparksEmitter.setEndSize(.5f);
        sparksEmitter.setLowLife(.4f);
        sparksEmitter.setHighLife(.8f);
        sparksEmitter.setParticlesPerSec(0);

        ParticleEmitter fireEmitter = new ParticleEmitter("Fire Emitter", ParticleMesh.Type.Triangle, 60);
        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        rootNode.attachChild(fireEmitter);
        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/flame.png"));
        fireEmitter.setMaterial(fireMat);
        fireEmitter.setImagesX(2);
        fireEmitter.setImagesY(2);
        fireEmitter.setSelectRandomImage(true);
        fireEmitter.setRandomAngle(true);
        fireEmitter.setStartColor(new ColorRGBA(1f, 1f, .5f, 1f));
        fireEmitter.setEndColor(new ColorRGBA(1f, 0f, 0f, 0f));
        fireEmitter.setGravity(0, 0, 0);
        fireEmitter.getParticleInfluencer().setVelocityVariation(0.5f);
        fireEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3f, 0));
        fireEmitter.setLowLife(0.5f);
        fireEmitter.setHighLife(2f);
        fireEmitter.setStartSize(1f);
        fireEmitter.setEndSize(0.05f);
        fireEmitter.setParticlesPerSec(0);

        ParticleEmitter burstEmitter = new ParticleEmitter("Burst Emitter", ParticleMesh.Type.Triangle, 5);
        rootNode.attachChild(burstEmitter);
        Material burstMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        burstMat.setTexture("Texture", assetManager.loadTexture("Textures/flash.png"));
        burstEmitter.setImagesX(2);
        burstEmitter.setImagesY(2);
        burstEmitter.setSelectRandomImage(true);
        burstEmitter.setMaterial(burstMat);
        burstEmitter.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, 1f));
        burstEmitter.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        burstEmitter.setStartSize(.1f);
        burstEmitter.setEndSize(5.0f);
        burstEmitter.setGravity(0, 0, 0);
        burstEmitter.setLowLife(.1f);
        burstEmitter.setHighLife(.2f);
        burstEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 5f, 0));
        burstEmitter.getParticleInfluencer().setVelocityVariation(1f);
        burstEmitter.setShape(new EmitterSphereShape(Vector3f.ZERO, .5f));
        burstEmitter.setParticlesPerSec(0);

        ParticleEmitter smokeEmitter = new ParticleEmitter("Dust Emitter", Type.Triangle, 100);
        Material smokeMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        smokeMat.setTexture("Texture", assetManager.loadTexture("Textures/smoke.png"));
        smokeEmitter.setMaterial(smokeMat);
        smokeEmitter.setImagesX(2);
        smokeEmitter.setImagesY(2);
        smokeEmitter.setSelectRandomImage(true);
        smokeEmitter.setRandomAngle(true);
        smokeEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2f, 0));
        smokeEmitter.getParticleInfluencer().setVelocityVariation(.5f);
        smokeEmitter.setParticlesPerSec(0);
        rootNode.attachChild(smokeEmitter);

        ParticleEmitter shockwaveEmitter = new ParticleEmitter("Shockwave Emitter", Type.Triangle, 1);
        Material shockwaveMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        shockwaveMat.setTexture("Texture", assetManager.loadTexture("Textures/shockwave.png"));
        shockwaveEmitter.setMaterial(shockwaveMat);
        shockwaveEmitter.setImagesX(1);
        shockwaveEmitter.setImagesY(1);
        shockwaveEmitter.setLowLife(1);
        shockwaveEmitter.setHighLife(1);
        shockwaveEmitter.setStartColor(new ColorRGBA(1, .8f, 0, 1));
        shockwaveEmitter.setEndColor(new ColorRGBA(.5f, .1f, .1f, 0));
        shockwaveEmitter.setStartSize(.1f);
        shockwaveEmitter.setEndSize(10f);
        shockwaveEmitter.setParticlesPerSec(0);
        rootNode.attachChild(shockwaveEmitter);

        ParticleEmitter emberEmitter = new ParticleEmitter("Ember Emitter", Type.Triangle, 20);
        Material emberMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        emberMat.setTexture("Texture", assetManager.loadTexture("Textures/embers.png"));
        emberEmitter.setMaterial(emberMat);
        emberEmitter.setImagesX(1);
        emberEmitter.setImagesY(1);
        emberEmitter.getParticleInfluencer().setVelocityVariation(.3f);
        emberEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2f, 0));
        emberEmitter.setLowLife(.5f);
        emberEmitter.setHighLife(2f);
        emberEmitter.setStartColor(new ColorRGBA(1, 1, 0, 1));
        emberEmitter.setEndColor(new ColorRGBA(1, 0, 0, 0));
        emberEmitter.setStartSize(.5f);
        emberEmitter.setEndSize(1f);
        emberEmitter.setParticlesPerSec(0);
        rootNode.attachChild(emberEmitter);

        /*ParticleEmitter debrisEmitter = new ParticleEmitter("Debris Emitter", Type.Triangle, 100);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/debris.png"));
        debrisEmitter.setMaterial(debrisMat);
        debrisEmitter.setImagesX(3);
        debrisEmitter.setImagesY(3);
        debrisEmitter.setStartSize(.5f);
        debrisEmitter.setEndSize(.5f);
        debrisEmitter.setLowLife(1);
        debrisEmitter.setHighLife(1);
        debrisEmitter.getParticleInfluencer().setVelocityVariation(1);
        debrisEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 10, 0));
        debrisEmitter.setRandomAngle(true);
        debrisEmitter.setRotateSpeed(50);
        debrisEmitter.setGravity(new Vector3f(0, 50, 0));
        rootNode.attachChild(debrisEmitter);*/

        inputManager.addMapping(MAPPING_EXPLODE, TRIGGER_EXPLODE);
        ActionListener explodeListener = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals(MAPPING_EXPLODE) && !isPressed) {
                    SceneGraphVisitorAdapter myEmitterAdapter = new SceneGraphVisitorAdapter() {
                        @Override
                        public void visit(Geometry geom) {
                            super.visit(geom);
                            searchForEmitter(geom);
                        }
                        @Override
                        public void visit(Node node) {
                            super.visit(node);
                            searchForEmitter(node);
                        }
                        private void searchForEmitter(Spatial spatial) {
                            if (spatial instanceof ParticleEmitter) {
                                System.out.println("Emitter in "+spatial.getName());
                                ((ParticleEmitter)spatial).emitAllParticles();
                                ((ParticleEmitter)spatial).setParticlesPerSec(0);
                            }
                        }
                    };
                    rootNode.depthFirstTraversal(myEmitterAdapter);
                }
            }
        };
        inputManager.addListener(explodeListener, MAPPING_EXPLODE);

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
