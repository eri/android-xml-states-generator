package com.erioxyde.app.asg.ui;

import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import com.erioxyde.app.asg.Constants;
import com.erioxyde.app.asg.Constants.StateRessourceDropListener;
import com.erioxyde.app.asg.bo.StateAttributes;

public class DropWindow
    extends FrameWindow
    implements StateRessourceDropListener
{

  public final class DrawableDropTarget
      extends DropTarget
  {

    private static final long serialVersionUID = 1L;

    private final StateRessourceDropListener stateRessourceDropListener;

    public DrawableDropTarget(StateRessourceDropListener stateRessourceDropListener)
        throws HeadlessException
    {
      super();
      this.stateRessourceDropListener = stateRessourceDropListener;
      try
      {
        addDropTargetListener(new DropTargetAdapter()
        {
          @Override
          public void drop(DropTargetDropEvent dtde)
          {
            // Do nothing !
          }

          @Override
          public void dragOver(DropTargetDragEvent event)
          {
            dropPanel.setIcon(dropIconOver);
          }

          @Override
          public void dragExit(DropTargetEvent event)
          {
            dropPanel.setIcon(dropIconDefault);
          }

        });
      }
      catch (TooManyListenersException exception)
      {
        exception.printStackTrace();
      }
    }

    @Override
    public synchronized void drop(DropTargetDropEvent dropEvent)
    {
      try
      {
        dropEvent.acceptDrop(DnDConstants.ACTION_COPY);
        final List<File> droppedFiles = (List<File>) dropEvent.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
        final List<StateAttributes> ressources = new ArrayList<StateAttributes>();
        for (File file : droppedFiles)
        {
          final String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();
          if (extension.equals(Constants.DRAWABLE_EXTENSION) == true)
          {
            ressources.add(new StateAttributes(file));
          }
        }
        stateRessourceDropListener.onDrop(ressources);
      }
      catch (Exception exception)
      {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(window, exception.getMessage());
      }
    }
  }

  private JLabel dropPanel;

  private ImageIcon dropIconDefault;

  private ImageIcon dropIconOver;

  public DropWindow()
  {
    super(Constants.APP_TITLE, 320, 240);
    createDropZone();
  }

  private void createDropZone()
  {
    dropIconDefault = new ImageIcon(retrieveIcon("ressources/dropzone.png"));
    dropIconOver = new ImageIcon(retrieveIcon("ressources/dropzone_over.png"));
    dropPanel = new JLabel(dropIconDefault);
    dropPanel.setDropTarget(new DrawableDropTarget(this));
    window.add(dropPanel);
  }

  private BufferedImage retrieveIcon(String path)
  {
    BufferedImage dropIcon = null;
    try
    {
      dropIcon = ImageIO.read(new File(path));
    }
    catch (IOException exception)
    {
      exception.printStackTrace();
      // JOptionPane.showMessageDialog(window, exception.getMessage());
    }
    return dropIcon;
  }

  @Override
  public void onDrop(List<StateAttributes> ressources)
  {
    dropPanel.setIcon(dropIconDefault);
    if (ressources.size() > 0)
    {
      final XmlPreviewWindow xmlWindow = new XmlPreviewWindow(ressources);
      xmlWindow.show();
    }
    else
    {
      JOptionPane.showMessageDialog(window, "Drop valid Android Drawable files ! (*.png / *.9.png)");
    }
  }

}
