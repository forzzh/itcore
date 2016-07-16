package www.core.com.Enum;

public enum VerifyCodeEnum implements Enum {
	delete{//删除状态
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "1";
		}
	},
	nodelete{//不删除状态
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "0";
		}
	},
	use{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "已使用";
		}
	},
	nouse{	
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "未使用";
		}
	},
}
