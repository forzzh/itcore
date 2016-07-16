package www.core.com.Enum;

public enum VerifyCodeTypeEnum implements Enum {

	findpassword {
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "找回密码类型";
		}
	},
	activeUser {
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "激活用户";
		}
	}
}
