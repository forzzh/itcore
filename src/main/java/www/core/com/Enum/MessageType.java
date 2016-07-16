package www.core.com.Enum;

public enum MessageType  implements Enum  {
	addGroup
	{
		@Override
		public String show() {
			return "添加圈子信息";
		}
	},
	exitGroup{
		@Override
		public String show(){
			return "退出添加圈子信息";
		}
	},
	newReply{
		@Override
		public String show() {
			return "新的回复";
		}	
	},
	newReplyBook{
		@Override
		public String show() {
			return "新的图书回复";
		}
	},
	applyGroup{
		@Override
		public String show() {
			return "圈子申请";
		}
	}
	,READadd
	{
		@Override
		public String show() {
			return "已读添加圈子信息";
		}
	},
}
