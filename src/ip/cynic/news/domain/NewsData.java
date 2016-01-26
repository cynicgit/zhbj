package ip.cynic.news.domain;

import java.util.List;

public class NewsData {

	public List<NewsMenuData> data;

	public List<String> extend;

	public class NewsMenuData {
		public String id;
		public String title;
		public String type;
		public List<NewsTabData> children;
	}

	public class NewsTabData {
		public String id;
		public String title;
		public String type;
		public String url;
	}

}
