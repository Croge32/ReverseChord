package com.reversechord;

import java.util.List;

public class APIResponse {

	private List<Objects> objects;
	
	public static class Objects {
		private String code;
		private String image_url;
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String c) {
			code = c;
		}
		
		public String getImg() {
			return image_url;
		}
		
		public void setImg(String img) {
			image_url = img;
		}
	}
	
	public String getImg() {
		return objects.get(0).getImg();
	}
	
	public String getCode() {
		return objects.get(0).getCode();
	}
}
