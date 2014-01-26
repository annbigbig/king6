package com.helloweenvsfei.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class UploadRequestWrapper extends HttpServletRequestWrapper {

	private static final String MULTIPART_HEADER = "Content-type";

	// �O�_�O�W���ɮ�
	private boolean multipart;

	// map�A�x�s�Ҧ�����
	private Map<String, Object> params = new HashMap<String, Object>();

	@SuppressWarnings("all")
	public UploadRequestWrapper(HttpServletRequest request) {

		super(request);

		// �P�_�O�_���W���ɮ�
		multipart = request.getHeader(MULTIPART_HEADER) != null
				&& request.getHeader(MULTIPART_HEADER).startsWith(
						"multipart/form-data");

		if (multipart) {

			try {
				// �ϥ�apache���u��ѪR
				DiskFileUpload upload = new DiskFileUpload();
				upload.setHeaderEncoding("utf8");

				// �ѪR�A��o�Ҧ�����r��P�ɮװ�
				List<FileItem> fileItems = upload.parseRequest(request);

				for (Iterator<FileItem> it = fileItems.iterator(); it.hasNext();) {

					// �ˬd
					FileItem item = it.next();
					if (item.isFormField()) {

						// �p�G�O��r��A�������map��
						params.put(item.getFieldName(), item.getString("utf8"));

					} else {

						// �_�h�A���ɮסA����o�ɮצW��
						String filename = item.getName().replace("\\", "/");
						filename = filename
								.substring(filename.lastIndexOf("/") + 1);

						//add by annbigbig
						System.out.println("From UploadRequestWrapper :: filename = [" + filename + "]\n");
						if(filename==null || filename.length()==0) continue;
						
						// �x�s��t���{���ɮק���
						File file = new File(System
								.getProperty("java.io.tmpdir"), filename);
						System.out.println("from Upload RequestWrapper :: File = " + file);

						// �x�s�ɮפ��e
						OutputStream ous = new FileOutputStream(file);
						ous.write(item.get());
						ous.close();

						// ���map��
						params.put(item.getFieldName(), file);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object getAttribute(String name) {

		// �p�G���W���ɮסA�h�qmap�����
		if (multipart && params.containsKey(name)) {
			return params.get(name);
		}
		return super.getAttribute(name);
	}

	@Override
	public String getParameter(String name) {

		// �p�G���W���ɮסA�h�qmap�����
		if (multipart && params.containsKey(name)) {
			return params.get(name).toString();
		}
		return super.getParameter(name);
	}

	public static void main(String[] args) {

		System.out.println(System.getProperties().toString().replace(", ",
				"\r\n"));

	}

}
