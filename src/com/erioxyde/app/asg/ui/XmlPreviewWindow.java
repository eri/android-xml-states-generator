package com.erioxyde.app.asg.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FilenameUtils;

import com.erioxyde.app.asg.Constants;
import com.erioxyde.app.asg.Constants.StateList;
import com.erioxyde.app.asg.bo.ItemType;
import com.erioxyde.app.asg.bo.ObjectFactory;
import com.erioxyde.app.asg.bo.SelectorType;
import com.erioxyde.app.asg.bo.StateAttributes;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public final class XmlPreviewWindow
    extends FrameWindow
{

  public XmlPreviewWindow(final List<StateAttributes> ressources)
  {
    super(Constants.APP_TITLE, 640, 480);
    try
    {
      final String xml = computeXml(ressources);

      final File rootFile = ressources.size() > 0 ? ressources.get(0).file : null;
      final String xmlFileName = computeXmlFileName(rootFile);

      final JPanel panel = new JPanel();
      window.getContentPane().add(panel, BorderLayout.SOUTH);

      final JButton saveAsButton = new JButton("Save to...");
      saveAsButton.addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseClicked(MouseEvent event)
        {
          final JFileChooser fileChooser = rootFile != null ? new JFileChooser(rootFile.getAbsolutePath()) : new JFileChooser();
          fileChooser.setSelectedFile(new File(rootFile, xmlFileName));
          final FileFilter filter = new FileFilter()
          {
            @Override
            public String getDescription()
            {
              return Constants.SAVE_AS_XML_DESCRIPTION;
            }

            @Override
            public boolean accept(File file)
            {
              return file.isDirectory() || FilenameUtils.getExtension(file.getName().toLowerCase()).equals(Constants.XML_EXTENSION);
            }
          };
          fileChooser.setFileFilter(filter);
          fileChooser.setMultiSelectionEnabled(false);
          if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION)
          {
            final File file = fileChooser.getSelectedFile();
            try
            {
              writeFile(file, xml);
            }
            catch (IOException exception)
            {
              exception.printStackTrace();
              JOptionPane.showMessageDialog(window, exception.getMessage());
            }
          }
        }
      });

      final JButton saveButton = new JButton("Save to '" + xmlFileName + "'");
      saveButton.addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseClicked(MouseEvent event)
        {
          try
          {
            final File destinationFile = new File(FilenameUtils.getFullPath(rootFile.getAbsolutePath()), xmlFileName);
            if (destinationFile.exists() == false)
            {
              destinationFile.createNewFile();
            }
            writeFile(destinationFile, xml);
          }
          catch (IOException exception)
          {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(window, exception.getMessage());
          }
        }
      });

      panel.add(saveButton);
      panel.add(saveAsButton);

      final JTextPane textPreview = new JTextPane();
      textPreview.setText(xml);
      window.getContentPane().add(textPreview, BorderLayout.CENTER);

      final JList ressourceList = new JList();
      window.getContentPane().add(ressourceList, BorderLayout.EAST);

    }
    catch (JAXBException exception)
    {
      exception.printStackTrace();
      JOptionPane.showMessageDialog(window, exception.getMessage());
    }
  }

  private void writeFile(File file, String xml)
      throws IOException
  {
    FileWriter fstream = null;
    BufferedWriter out = null;
    try
    {
      fstream = new FileWriter(file);
      out = new BufferedWriter(fstream);
      out.write(xml);
      out.flush();
    }
    catch (IOException exception)
    {
      throw exception;
    }
    finally
    {
      if (out != null)
      {
        out.close();
      }
      if (fstream != null)
      {
        fstream.close();
      }
    }
  }

  private String computeXmlFileName(final File rootFile)
  {
    if (rootFile == null)
    {
      return "states.xml";
    }
    final String baseName = Constants.getCleanFileName(rootFile);
    for (StateList state : StateList.values())
    {
      if (baseName.endsWith(state.toString()) == true)
      {
        return baseName.replace(state.toString(), "") + Constants.DOT_XML_EXTENSION;
      }
    }
    return baseName + Constants.DOT_XML_EXTENSION;
  }

  private String computeXml(List<StateAttributes> ressources)
      throws JAXBException
  {
    final ObjectFactory factory = new ObjectFactory();
    final List<ItemType> items = new ArrayList<ItemType>();
    for (StateAttributes ressource : ressources)
    {
      final ItemType item = factory.createItemType();
      // Pressed State
      if (ressource.isPressed() == true)
      {
        item.setStatePressed(Constants.TRUE);
      }
      // Focused State
      else if (ressource.isFocused() == true)
      {
        item.setStateFocused(Constants.TRUE);
      }
      // Selected State
      else if (ressource.isSelected() == true)
      {
        item.setStateSelected(Constants.TRUE);
      }
      // Activated State
      else if (ressource.isActivated() == true)
      {
        item.setStateActivated(Constants.TRUE);
      }
      // Focused State
      else if (ressource.isFocused() == true)
      {
        item.setStateFocused(Constants.TRUE);
      }
      // Hovered State
      else if (ressource.isHovered() == true)
      {
        item.setStateHovered(Constants.TRUE);
      }
      // Checkable State
      else if (ressource.isCheckable() == true)
      {
        item.setStateCheckable(Constants.TRUE);
      }
      // Checked State
      else if (ressource.isChecked() == true)
      {
        item.setStateChecked(Constants.TRUE);
      }
      // WindowFocused State
      else if (ressource.isWindowFocused() == true)
      {
        item.setStateWindowFocused(Constants.TRUE);
      }
      // Enabled State
      if (ressource.isEnabled != null)
      {
        item.setStateEnabled(ressource.isEnabled() == true ? Constants.TRUE : Constants.FALSE);
      }
      item.drawable = Constants.DRAWABLE_PREFIX + ressource.baseName;
      items.add(item);
    }
    final SelectorType selector = factory.createSelectorType();
    selector.item = items;

    final JAXBContext context = JAXBContext.newInstance(SelectorType.class);
    final JAXBElement<SelectorType> element = factory.createSelector(selector);
    final Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty("jaxb.formatted.output", true);
    marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper()
    {
      @Override
      public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
      {
        return Constants.XML_ANDROID_NAMESPACE;
      }
    });
    final StringWriter writer = new StringWriter();
    marshaller.marshal(element, writer);
    return writer.toString();
  }

}
