/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cylinderarm;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.LineArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author Albert, Michal
 */
public class CylinderArm  extends Applet implements KeyListener
{
    SimpleBehavior ArmBehavior;
    Appearance ap = new Appearance();
    Color3f TestCol = new Color3f(0.941f, 0.941f, 0.937f);
    Color3f ultramaryna = new Color3f(0.227f, 0.909f, 0.949f); //
    //KeyPressed, keyReleased ,keyTyped will allow handling inputs
    public void keyPressed(KeyEvent e) 
    {
          
        
    }
    
    public void keyReleased(KeyEvent e)
    {
    
        
    }
     
    public void keyTyped(KeyEvent e)
    {
        
        
    }
      
    public BranchGroup createSceneGraph()
      {
          //Creating base node and adding others
          BranchGroup objRoot = new BranchGroup();
          TransformGroup transBox = new TransformGroup();
          transBox.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          TransformGroup CylinderR=new TransformGroup();
          CylinderR.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
          
          objRoot.addChild(transBox);
          objRoot.addChild(CylinderR);
          ap.setMaterial(new Material( ultramaryna,ultramaryna,ultramaryna,ultramaryna,0.1f));
          //Adds stable, base component
          transBox.addChild(new Box(0.5f,0.5f,0.5f,Box.GENERATE_TEXTURE_COORDS,ap));
          //Adds Cylinder that can be rotate around own axis
          CylinderR.addChild(new Cylinder(0.25f,5.f,Cylinder.GENERATE_TEXTURE_COORDS,ap));
          //Adds simple texture to ap Appearance
          TextureLoader loader = new TextureLoader("gfx/Metal_Brushed.png",null);
          ImageComponent2D mImage = loader.getImage( );     
          Texture2D  tx2 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
          tx2.setImage(0, mImage);
          ap.setTexture(tx2);
          //Lightning
          BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0f);
          AmbientLight alldirlight = new AmbientLight(ultramaryna);
          alldirlight.setInfluencingBounds(bounds);
          objRoot.addChild(alldirlight); // adds light in all direction 
          Color3f lightCol = new Color3f(0.7f, 0.5f, 0.2f);
          Vector3f lightDir = new Vector3f(4.0f, -7.0f, -12.0f);
          DirectionalLight light = new DirectionalLight (lightCol,lightDir);
          light.setInfluencingBounds(bounds);
          objRoot.addChild(light);// adds light in specific direction
          
          ArmBehavior = new SimpleBehavior(CylinderR);
          ArmBehavior.setSchedulingBounds(bounds);
          objRoot.addChild(ArmBehavior);
          
          objRoot.compile();
          return objRoot;
          
      }
    
     public CylinderArm()
     {
         GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration(); 
         setLayout(new BorderLayout()); 
         //tworzymy nasze plotno
         Canvas3D canvas3d = new Canvas3D(config);
         add("Center", canvas3d);
         canvas3d.addKeyListener(this);
         BranchGroup scena = createSceneGraph(); 

         SimpleUniverse simpleU = new SimpleUniverse(canvas3d);
        // simpleU.getViewingPlatform().setNominalViewingTransform();
         OrbitBehavior orbit = new OrbitBehavior(canvas3d, OrbitBehavior.REVERSE_ROTATE); //allow rotating camera with mouse
         orbit.setSchedulingBounds(new BoundingSphere());    
         simpleU.getViewingPlatform().setViewPlatformBehavior(orbit);                        
         //simpleU.getViewingPlatform().getViewPlatformTransform();  
         simpleU.getViewingPlatform().setNominalViewingTransform();                      //nice view from the beggining
         simpleU.addBranchGraph(scena);
     }

     public static void main(String[] args) 
     {
        System.setProperty("sun.awt.erasebackground", "true");

   CylinderArm test = new CylinderArm();
  // test.addKeyListener(test);


   new MainFrame(test, 1000, 800);
    }
    
}
