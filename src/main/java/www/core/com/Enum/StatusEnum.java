package www.core.com.Enum;

public enum StatusEnum  implements Enum {
	delete{//删除状态
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "删除";
		}
	},
	nodelete{//不删除状态
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "正常";
		}
	},
	//圈子添加同意状态
	agree{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "2";
		}
	},
	//圈子添加不同意
	noagree{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "3";
		}
	},
	
	read{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "3";
		}
	},
	noread{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "4";
		}
	},
	ok{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "同意";
		}
	},
	no{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "不同意";
		}
	},//圈子审核中状态
	pending{
		@Override
		public String show() {
			return "审核中";
		}		
	}
	
}
