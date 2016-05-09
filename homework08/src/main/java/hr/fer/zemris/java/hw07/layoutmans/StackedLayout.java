/**
 * 
 */
package hr.fer.zemris.java.hw07.layoutmans;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.PrintStream;

import javax.swing.SizeRequirements;

/**
 * @author Ivan
 * 
 */
public class StackedLayout implements LayoutManager2 {

	/**
	 * Specifies that components should be laid out left to right.
	 */
	public static final int X_AXIS = 0;

	/**
	 * Specifies that components should be laid out top to bottom.
	 */
	public static final int Y_AXIS = 1;

	/**
	 * Specifies that components should be laid out in the direction of a line of text as determined by the
	 * target container's <code>ComponentOrientation</code> property.
	 */
	public static final int LINE_AXIS = 2;

	/**
	 * Specifies that components should be laid out in the direction that lines flow across a page as
	 * determined by the target container's <code>ComponentOrientation</code> property.
	 */
	public static final int PAGE_AXIS = 3;

	private int kind;

	/**
	 * 
	 */
	public StackedLayout(int kind) {
		axis = Y_AXIS;
		this.kind = kind;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// nista

	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// nista

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension size;
		checkRequests(parent);
		size = new Dimension(xTotal.preferred, yTotal.preferred);

		Insets insets = parent.getInsets();
		size.width = (int) Math.min((long) size.width + (long) insets.left + (long) insets.right,
				Integer.MAX_VALUE);
		size.height = (int) Math.min((long) size.height + (long) insets.top + (long) insets.bottom,
				Integer.MAX_VALUE);
		return size;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension size;
		checkRequests(parent);
		size = new Dimension(xTotal.minimum, yTotal.minimum);

		Insets insets = parent.getInsets();
		size.width = (int) Math.min((long) size.width + (long) insets.left + (long) insets.right,
				Integer.MAX_VALUE);
		size.height = (int) Math.min((long) size.height + (long) insets.top + (long) insets.bottom,
				Integer.MAX_VALUE);
		return size;
	}

	@Override
	public void layoutContainer(Container target) {
		int nChildren = target.getComponentCount();
		int[] xOffsets = new int[nChildren];
		int[] xSpans = new int[nChildren];
		int[] yOffsets = new int[nChildren];
		int[] ySpans = new int[nChildren];
		Dimension alloc = new Dimension();
		alloc.height = alloc.width = 0;

		if (kind == StackedLayoutDirection.FILL) {
			alloc = target.getSize();
		}
		else {
			for (int i = 0; i < nChildren; i++) {
				alloc.height += target.getComponent(i).getPreferredSize().height;
			}
		}
		
		alloc.width = target.getSize().width;
		Insets in = target.getInsets();
		alloc.width -= in.left + in.right;
		alloc.height -= in.top + in.bottom;

		// Resolve axis to an absolute value (either X_AXIS or Y_AXIS)
		ComponentOrientation o = target.getComponentOrientation();
		int absoluteAxis = resolveAxis(axis, o);
		boolean ltr = (absoluteAxis != axis) ? o.isLeftToRight() : true;

		checkRequests(target);

		SizeRequirements.calculateAlignedPositions(alloc.width, xTotal, xChildren, xOffsets, xSpans, ltr);
		SizeRequirements.calculateTiledPositions(alloc.height, yTotal, yChildren, yOffsets, ySpans);
		
		int position =target.getSize().height;
		for(int i=0;i<nChildren;i++) {
			position -= ySpans[i];
		}
		position-= (in.top+in.bottom);
		if(kind != StackedLayoutDirection.FROM_BOTTOM) {
			position = 0;
		}
		// flush changes to the container
		for (int i = 0; i < nChildren; i++) {
			Component c = target.getComponent(i);
			c.setBounds((int) Math.min((long) in.left + 0, Integer.MAX_VALUE),
					(int) Math.min(position+(long) in.top + (long) yOffsets[i], Integer.MAX_VALUE), alloc.width,
					ySpans[i]);

		}
		if (dbg != null) {
			for (int i = 0; i < nChildren; i++) {
				Component c = target.getComponent(i);
				dbg.println(c.toString());
				dbg.println("X: " + xChildren[i]);
				dbg.println("Y: " + yChildren[i]);
			}
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension size;
		checkRequests(target);
		size = new Dimension(xTotal.maximum, yTotal.maximum);

		Insets insets = target.getInsets();
		size.width = (int) Math.min((long) size.width + (long) insets.left + (long) insets.right,
				Integer.MAX_VALUE);
		size.height = (int) Math.min((long) size.height + (long) insets.top + (long) insets.bottom,
				Integer.MAX_VALUE);
		return size;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		checkRequests(target);
		return xTotal.alignment;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		checkRequests(target);
		return yTotal.alignment;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub

	}

	void checkRequests(Container target) {
		if (xChildren == null || yChildren == null) {
			// The requests have been invalidated... recalculate
			// the request information.
			int n = target.getComponentCount();
			xChildren = new SizeRequirements[n];
			yChildren = new SizeRequirements[n];
			for (int i = 0; i < n; i++) {
				Component c = target.getComponent(i);
				if (!c.isVisible()) {
					xChildren[i] = new SizeRequirements(0, 0, 0, c.getAlignmentX());
					yChildren[i] = new SizeRequirements(0, 0, 0, c.getAlignmentY());
					continue;
				}
				Dimension min = c.getMinimumSize();
				Dimension typ = c.getPreferredSize();
				Dimension max = c.getMaximumSize();
				xChildren[i] = new SizeRequirements(min.width, typ.width, max.width, c.getAlignmentX());
				yChildren[i] = new SizeRequirements(min.height, typ.height, max.height, c.getAlignmentY());
			}

			// Resolve axis to an absolute value (either X_AXIS or Y_AXIS)
			int absoluteAxis = resolveAxis(axis, target.getComponentOrientation());

			if (absoluteAxis == X_AXIS) {
				xTotal = SizeRequirements.getTiledSizeRequirements(xChildren);
				yTotal = SizeRequirements.getAlignedSizeRequirements(yChildren);
			}
			else {
				xTotal = SizeRequirements.getAlignedSizeRequirements(xChildren);
				yTotal = SizeRequirements.getTiledSizeRequirements(yChildren);
			}
		}
	}

	private int resolveAxis(int axis, ComponentOrientation o) {
		int absoluteAxis;
		if (axis == LINE_AXIS) {
			absoluteAxis = o.isHorizontal() ? X_AXIS : Y_AXIS;
		}
		else if (axis == PAGE_AXIS) {
			absoluteAxis = o.isHorizontal() ? Y_AXIS : X_AXIS;
		}
		else {
			absoluteAxis = axis;
		}
		return absoluteAxis;
	}

	private int axis;

	private transient SizeRequirements[] xChildren;
	private transient SizeRequirements[] yChildren;
	private transient SizeRequirements xTotal;
	private transient SizeRequirements yTotal;

	private transient PrintStream dbg;
}
