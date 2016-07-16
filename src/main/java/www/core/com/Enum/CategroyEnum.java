package www.core.com.Enum;

public enum CategroyEnum  implements Enum{

	topic{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "技术帖子";
		}
	}
	,project
	{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "软件发布";
		}
	},question{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "技术问答";
		}
	}
	
}
