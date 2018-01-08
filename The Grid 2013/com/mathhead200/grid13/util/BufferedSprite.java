package com.mathhead200.grid13.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.mathhead200.grid13.GridItem;
import com.mathhead200.grid13.gui.Sprite;


public class BufferedSprite extends GridItem implements Sprite
{
	private BufferedImage img;
	private AffineTransform trans;
	private BufferedImage transImg = null;
	private boolean updateTrans = true;

	public BufferedSprite(BufferedImage img) {
		this.img = img;
		this.trans = img.createGraphics().getTransform();
	}

	public void translate(double x, double y) {
		trans.preConcatenate( AffineTransform.getTranslateInstance(x, y) );
		updateTrans = true;
	}

	public void rotate(double angle, AngleType type) {
		trans.preConcatenate( AffineTransform.getRotateInstance(
				AngleType.convert(angle, type, AngleType.RADIAN)) );
		updateTrans = true;
	}

	public void rotate(double angle) {
		rotate(angle, AngleType.RADIAN);
	}

	public void scale(double x, double y) {
		trans.preConcatenate( AffineTransform.getScaleInstance(x, y) );
		updateTrans = true;
	}

	public void shear(double x, double y) {
		trans.preConcatenate( AffineTransform.getShearInstance(x, y) );
		updateTrans = true;
	}

	public Image getImage() {
		if( updateTrans ) {
			BufferedImage transImg = new BufferedImage( img.getWidth(), img.getHeight(), img.getType() );
			Graphics2D g2 = transImg.createGraphics();
			g2.transform(trans);
			g2.drawImage(img, 0, 0, null);
			this.transImg = transImg;
			updateTrans = false;
		}
		return this.transImg;
	}
}
