package com.mathhead200.image;

import com.mathhead200.MH;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Slideshow extends JLabel implements Runnable
{
	private List<BufferedImage> slides;
	private int slideTime;
	private int fadeTime;
	private int smoothness;
	private boolean randomizable;

	public Slideshow(List<BufferedImage> imgList, Dimension dim, int slideTime, int fadeTime, int smoothness, boolean randomizable) {
		if( imgList != null )
			slides = imgList;
		else
			slides = new ArrayList<BufferedImage>(0);

		if( dim == null )
			setPreferredSize(dim);
		else
			autoScale();

		this.slideTime = slideTime;
		this.fadeTime = fadeTime;
		this.smoothness = smoothness > 0 ? smoothness : 255;
		this.randomizable = randomizable;
	}

	public Slideshow(List<BufferedImage> imgList, Dimension dim) {
		this(imgList, dim, 5000, 1000, 5, false);
	}

	public Slideshow(List<BufferedImage> imgList) {
		this(imgList, null);
	}

	public Slideshow() {
		this(null);
	}


	public void run() {
		INF_LOOP: while( slides.size() > 0 ) {
			for( BufferedImage slide : slides ) {
				EquationImage img = new EquationImage(slide);
				setIcon(img);
				try {
					Thread.sleep(slideTime);
				} catch (InterruptedException e) { break INF_LOOP; }
				//fade slide
				for( int _ = 0; _ < 255; _ += smoothness ) {
					img = img.tint(0, 0, 0, -smoothness);
					try {
						Thread.sleep( fadeTime / 255 * smoothness );
					} catch (InterruptedException e) { break INF_LOOP; }
					setIcon(img);
				}
			}
			if(randomizable)
				randomize();
		}
	}


	public List<BufferedImage> getSlides() {
		return slides;
	}

	public int getSlideTime() {
		return slideTime;
	}

	public int getFadeTime() {
		return fadeTime;
	}

	public int getSmoothness() {
		return smoothness;
	}

	public boolean isRandomizable() {
		return randomizable;
	}

	public void setSlideTime(int slideTime) {
		this.slideTime = slideTime;
	}

	public void setFadeTime(int fadeTime) {
		this.fadeTime = fadeTime;
	}

	public void setSmoothness(int smoothness) {
		this.smoothness = smoothness;
	}

	public void setRandomizable(boolean randomizable) {
		this.randomizable = randomizable;
	}

	public void setIcon(Image img) {
		setIcon( new ImageIcon(img) );
	}

	public void autoScale() {
		int maxWidth = 0, maxHeight = 0;
		for( BufferedImage img : slides ) {
			if( img.getWidth() > maxWidth )
				maxWidth = img.getWidth();
			if( img.getHeight() > maxHeight )
				maxHeight = img.getHeight();
		}
		setPreferredSize( new Dimension(maxWidth, maxHeight) );
	}

	public void randomize() {
		MH.shuffle(slides);
	}
}
