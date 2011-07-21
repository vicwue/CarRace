package min3d.sampleProject1;

import android.view.*;
import min3d.Shared;
import min3d.Utils;
import min3d.objectPrimitives.*;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Color4;
import min3d.vos.Light;
import min3d.vos.Number3d;


public class ExampleLoadObjFile extends RendererActivity {
	private Object3dContainer objModel;
	private Object3dContainer Welt;
	private Number3d Kamera;
	private Number3d Ziel;
	private float worldz = 0;
	private float worldy = 0;
	private float worldx = 0;
	private Display display;
	private int width;
	private int height;
	int range = 3;
	int ziel = 300; // LŠnge der Strecke
	@Override
	public void initScene() {
		Light licht = new Light();
		scene.lights().add(licht);
		licht.position.setAll(10f, 100f, -60f);
		
		
		Kamera = scene.camera().position;
		Ziel = scene.camera().target;
		
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "min3d.sampleProject1:raw/camaro_obj", true);
		parser.parse();
		
		
		for (int i = 0; i < (ziel/6); i++) {
			Welt = (Object3dContainer) new Box(0.3f,0.3f,1f, new Color4(254,1,1, 255)).positionsetAll(range, 0.2f, -i*6);
			scene.addChild(Welt);
			Welt = (Object3dContainer) new Box(0.3f,0.3f,1f, new Color4(0,254,0, 255)).positionsetAll(range, 0.2f, -(i+2)*6);
			scene.addChild(Welt);
			Welt = (Object3dContainer) new Box(0.3f,0.3f,1f, new Color4(0,0,254, 255)).positionsetAll(range, 0.2f, -(i+4)*6);

			scene.addChild(Welt);
			
			Welt = (Object3dContainer) new Box(0.3f,0.3f,1f, new Color4(254,0,0, 255)).positionsetAll(-range, 0.2f, -i*6);
			scene.addChild(Welt);
			Welt = (Object3dContainer) new Box(0.3f,0.3f,1f, new Color4(0,254,0, 255)).positionsetAll(-range, 0.2f, -(i+2)*6);
			scene.addChild(Welt);
			Welt = (Object3dContainer) new Box(0.3f,0.3f,1f, new Color4(0,0,254, 255)).positionsetAll(-range, 0.2f, -(i+4)*6);
			scene.addChild(Welt);
			} 
		objModel = parser.getParsedObject();
		objModel.scale().x = objModel.scale().y = objModel.scale().z = .7f;
		objModel.position().setAll(0f, 0f, 0f);
		scene.addChild(objModel);
		
		objModel.rotation().x= -90;
		display = getWindowManager().getDefaultDisplay();
		
	}

	@Override
	public void updateScene() {
		//scene.camera().frustum.zNear(scene.camera().frustum.zNear()+1.0f);
		 
		
			worldz--;
		
		Kamera.y = 2 + worldy;
		Kamera.z =5 + worldz;
		objModel.position().x = worldx;
		Ziel.z = -10 + worldz;
		Ziel.y = 1 + worldy;

		objModel.position().z = worldz;

		
	
	}
	public boolean onTouchEvent(MotionEvent $e) {
		
		width = display.getWidth();
		height = display.getHeight();
		if (width > height) {
			//landscapeformat
			range = 3;
		} else {
			//portrait
			range = 2;
		}
		
		worldx = (range*2*$e.getX()/width-range);
		
		
        
        return true;
    }
}
