package www.core.com.Enum;

public enum UserRelationshipEnum implements Enum {

	follow {
		@Override
		public String show() {
			return "��ע";
		}
	},
	defollow {
		@Override
		public String show() {
			return "δ��ע";
		}
	}
}
