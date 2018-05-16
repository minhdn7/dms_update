package vn.com.vsc.ptpm.VNPT_DMS.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;


public class ReadOrWriterObject {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static final String DATA_PATH = "/sdcard/DMSDATA/";
	public static final String DST_NAME = "DST.txt"; // --------------------------------------------------------------------------------------------------
	// Fields
	// --------------------------------------------------------------------------------------------------
	File file;

	// ===========================================================
	// Constructors
	// ===========================================================
	public ReadOrWriterObject() {
		file = new File(DATA_PATH + DST_NAME);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// --------------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// --------------------------------------------------------------------------------------------------

	// ===========================================================
	// Methods
	// ===========================================================

	public List<Tuyen> readPlace() {
		List<Tuyen> listPlaceDetail = new ArrayList<Tuyen>();
		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file));
				listPlaceDetail = (ArrayList<Tuyen>) ois.readObject();
				ois.close();
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPlaceDetail;
	}

	public void writePlace(ArrayList<Tuyen> listPlaceDetail) {
		File d = new File(DATA_PATH);
		if (!d.exists()) {
			d.mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(listPlaceDetail);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
