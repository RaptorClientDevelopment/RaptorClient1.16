package com.lukflug.panelstudio.mc16fabric;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.lukflug.panelstudio.base.IInterface;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Implementation of {@link IInterface} for OpenGL in Minecraft.
 * @author lukflug
 */
public abstract class GLInterface implements IInterface {
	/**
	 * Clipping rectangle stack.
	 */
	private final Stack<Rectangle> clipRect=new Stack<Rectangle>();
	/**
	 * Boolean indicating whether to clip in the horizontal direction. 
	 */
	protected boolean clipX;
	/**
	 * List of loaded images.
	 */
	protected final List<Identifier> textures=new ArrayList<Identifier>();
	
	/**
	 * Constructor.
	 * @param clipX whether to clip in the horizontal direction
	 */
	public GLInterface (boolean clipX) {
		this.clipX=clipX;
	}
	
	@Override
	public Dimension getWindowSize() {
		return new Dimension((int)Math.ceil(getScreenWidth()),(int)Math.ceil(getScreenHeight()));
	}

	@SuppressWarnings("resource")
	@Override
	public void drawString (Point pos, int height, String s, Color c) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef(pos.x,pos.y,0);
		double scale=height/(double)MinecraftClient.getInstance().textRenderer.fontHeight;
		GlStateManager.scaled(scale,scale,1);
		end(false);
		MinecraftClient.getInstance().textRenderer.drawWithShadow(getMatrixStack(),s,0,0,c.getRGB());
		begin(false);
		GlStateManager.popMatrix();
	}

	@SuppressWarnings("resource")
	@Override
	public int getFontWidth (int height, String s) {
		double scale=height/(double)MinecraftClient.getInstance().textRenderer.fontHeight;
		return (int)Math.round(MinecraftClient.getInstance().textRenderer.getWidth(s)*scale);
	}

	@Override
	public void fillTriangle (Point pos1, Point pos2, Point pos3, Color c1, Color c2, Color c3) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_TRIANGLES,VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(pos1.x,pos1.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
			bufferbuilder.vertex(pos2.x,pos2.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
			bufferbuilder.vertex(pos3.x,pos3.y,getZLevel()).color(c3.getRed()/255.0f,c3.getGreen()/255.0f,c3.getBlue()/255.0f,c3.getAlpha()/255.0f).next();
		tessellator.draw();
	}

	@Override
	public void drawLine (Point a, Point b, Color c1, Color c2) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINES,VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(a.x,a.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
			bufferbuilder.vertex(b.x,b.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
		tessellator.draw();
	}

	@Override
	public void fillRect (Rectangle r, Color c1, Color c2, Color c3, Color c4) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(r.x,r.y+r.height,getZLevel()).color(c4.getRed()/255.0f,c4.getGreen()/255.0f,c4.getBlue()/255.0f,c4.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y+r.height,getZLevel()).color(c3.getRed()/255.0f,c3.getGreen()/255.0f,c3.getBlue()/255.0f,c3.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x,r.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
		tessellator.draw();
	}

	@Override
	public void drawRect (Rectangle r, Color c1, Color c2, Color c3, Color c4) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINE_LOOP, VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(r.x,r.y+r.height,getZLevel()).color(c4.getRed()/255.0f,c4.getGreen()/255.0f,c4.getBlue()/255.0f,c4.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y+r.height,getZLevel()).color(c3.getRed()/255.0f,c3.getGreen()/255.0f,c3.getBlue()/255.0f,c3.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x,r.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
		tessellator.draw();
	}
	
	@Override
	public synchronized int loadImage (String name) {
		try {
			Identifier rl=new Identifier(getResourcePrefix()+name);
			if (!textures.contains(rl)) {
				MinecraftClient.getInstance().getTextureManager().loadTextureAsync(rl,null).get();
				textures.add(rl);
			}
			return textures.indexOf(rl);
		} catch (ExecutionException e) {
			e.printStackTrace();
			return 0;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void drawImage (Rectangle r, int rotation, boolean parity, int image, Color color) {
		if (image==0) return;
		int texCoords[][]={{0,1},{1,1},{1,0},{0,0}};
		for (int i=0;i<rotation%4;i++) {
			int temp1=texCoords[3][0],temp2=texCoords[3][1];
			texCoords[3][0]=texCoords[2][0];
			texCoords[3][1]=texCoords[2][1];
			texCoords[2][0]=texCoords[1][0];
			texCoords[2][1]=texCoords[1][1];
			texCoords[1][0]=texCoords[0][0];
			texCoords[1][1]=texCoords[0][1];
			texCoords[0][0]=temp1;
			texCoords[0][1]=temp2;
		}
		if (parity) {
			int temp1=texCoords[3][0],temp2=texCoords[3][1];
			texCoords[3][0]=texCoords[0][0];
			texCoords[3][1]=texCoords[0][1];
			texCoords[0][0]=temp1;
			texCoords[0][1]=temp2;
			temp1=texCoords[2][0];
			temp2=texCoords[2][1];
			texCoords[2][0]=texCoords[1][0];
			texCoords[2][1]=texCoords[1][1];
			texCoords[1][0]=temp1;
			texCoords[1][1]=temp2;
		}
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		float[] colorBuffer={color.getRed()/255.0f,color.getGreen()/255.0f,color.getBlue()/255.0f,color.getAlpha()/255.0f};
		MinecraftClient.getInstance().getTextureManager().bindTexture(textures.get(image));
		GL11.glTexEnvfv(GL11.GL_TEXTURE_ENV,GL11.GL_TEXTURE_ENV_COLOR,colorBuffer);
		GlStateManager.enableTexture();
		bufferbuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
			bufferbuilder.vertex(r.x,r.y+r.height,getZLevel()).texture(texCoords[0][0],texCoords[0][1]).next();
			bufferbuilder.vertex(r.x+r.width,r.y+r.height,getZLevel()).texture(texCoords[1][0],texCoords[1][1]).next();
			bufferbuilder.vertex(r.x+r.width,r.y,getZLevel()).texture(texCoords[2][0],texCoords[2][1]).next();
			bufferbuilder.vertex(r.x,r.y,getZLevel()).texture(texCoords[3][0],texCoords[3][1]).next();
		tessellator.draw();
		GlStateManager.disableTexture();
	}
	
	/**
	 * Utility function to set clipping rectangle.
	 * @param r the clipping rectangle
	 */
	protected void scissor (Rectangle r) {
		if (r==null) {
			GL11.glScissor(0,0,0,0);
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			return;
		}
		Point a=guiToScreen(r.getLocation()),b=guiToScreen(new Point(r.x+r.width,r.y+r.height));
		if (!clipX) {
			a.x=0;
			b.x=MinecraftClient.getInstance().getWindow().getWidth();
		}
		GL11.glScissor(Math.min(a.x,b.x),Math.min(a.y,b.y),Math.abs(b.x-a.x),Math.abs(b.y-a.y));
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	@Override
	public void window (Rectangle r) {
		if (clipRect.isEmpty()) {
			scissor(r);
			clipRect.push(r);
		} else {
			Rectangle top=clipRect.peek();
			if (top==null) {
				scissor(null);
				clipRect.push(null);
			} else {
				int x1,y1,x2,y2;
				x1=Math.max(r.x,top.x);
				y1=Math.max(r.y,top.y);
				x2=Math.min(r.x+r.width,top.x+top.width);
				y2=Math.min(r.y+r.height,top.y+top.height);
				if (x2>x1 && y2>y1) {
					Rectangle rect=new Rectangle(x1,y1,x2-x1,y2-y1);
					scissor(rect);
					clipRect.push(rect);
				} else {
					scissor(null);
					clipRect.push(null);
				}
			}
		}
	}

	@Override
	public void restore() {
		if (!clipRect.isEmpty()) {
			clipRect.pop();
			if (clipRect.isEmpty()) GL11.glDisable(GL11.GL_SCISSOR_TEST);
			else scissor(clipRect.peek());
		}
	}
	
	/**
	 * Utility function to convert screen pixel coordinates to PanelStudio GUI coordinates.
	 * @param p the screen coordinates 
	 * @return the corresponding GUI coordinates
	 */
	public Point screenToGui (Point p) {
		int resX=getWindowSize().width;
		int resY=getWindowSize().height;
		return new Point(p.x*resX/MinecraftClient.getInstance().getWindow().getWidth(),resY-p.y*resY/MinecraftClient.getInstance().getWindow().getHeight()-1);
	}
	
	/**
	 * Utility function to convert PanelStudio GUI coordinates to screen pixel coordinates.
	 * @param p the GUI coordinates 
	 * @return the corresponding screen coordinates
	 */
	public Point guiToScreen (Point p) {
		double resX=getScreenWidth();
		double resY=getScreenHeight();
		return new Point((int)Math.round(p.x*MinecraftClient.getInstance().getWindow().getWidth()/resX),(int)Math.round((resY-p.y)*MinecraftClient.getInstance().getWindow().getHeight()/resY));
	}
	
	/**
	 * Get the current screen width.
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return MinecraftClient.getInstance().getWindow().getScaledWidth();
	}
	
	/**
	 * Get the current screen height.
	 * @return the screen height
	 */
	protected double getScreenHeight() {
		return MinecraftClient.getInstance().getWindow().getScaledHeight();
	}
	
	/**
	 * Set OpenGL to the state used by the rendering methods.
	 * Should be called before rendering.
	 * @param matrix whether to set up the modelview matrix
	 */
	public void begin (boolean matrix) {
		if (matrix) {
			GlStateManager.matrixMode(GL11.GL_PROJECTION);
			GlStateManager.pushMatrix();
			GlStateManager.loadIdentity();
			GlStateManager.ortho(0,getScreenWidth(),getScreenHeight(),0,-3000,3000);
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
			GlStateManager.pushMatrix();
			GlStateManager.loadIdentity();
		}
		GlStateManager.enableBlend();
		GlStateManager.disableTexture();
		GlStateManager.disableAlphaTest();
		GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_ONE,GL11.GL_ZERO);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.lineWidth(2);
		// Set texture env mode to combine
		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL11.GL_TEXTURE_ENV_MODE,GL13.GL_COMBINE);
		// Set combine mode to modulate
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_COMBINE_RGB,GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_COMBINE_ALPHA,GL11.GL_MODULATE);
		// Set first argument to sampled texture
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_SOURCE0_RGB,GL11.GL_TEXTURE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_OPERAND0_RGB,GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_SOURCE0_ALPHA,GL11.GL_TEXTURE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_OPERAND0_ALPHA,GL11.GL_SRC_ALPHA);
		// Set second argument to env color
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_SOURCE1_RGB,GL13.GL_CONSTANT);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_OPERAND1_RGB,GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_SOURCE1_ALPHA,GL13.GL_CONSTANT);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV,GL13.GL_OPERAND1_ALPHA,GL11.GL_SRC_ALPHA);
	}
	
	/**
	 * Restore OpenGL to the state expected by Minecraft.
	 * Should be called after rendering.
	 * @param matrix whether to restore the modelview matrix
	 */
	public void end (boolean matrix) {
		GL11.glPopAttrib();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableAlphaTest();
		GlStateManager.enableTexture();
		GlStateManager.disableBlend();
		if (matrix) {
			GlStateManager.matrixMode(GL11.GL_PROJECTION);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
			GlStateManager.popMatrix();
		}
	}
	
	/**
	 * Get the z-coordinate to render everything.
	 * @return the z-level
	 */
	protected abstract float getZLevel();
	
	/**
	 * Get the matrix stack to be used.
	 * @return the current matrix stack
	 */
	protected abstract MatrixStack getMatrixStack();
	
	/**
	 * Get the Minecraft resource location string.
	 * @return the resource prefix
	 */
	protected abstract String getResourcePrefix();
}
