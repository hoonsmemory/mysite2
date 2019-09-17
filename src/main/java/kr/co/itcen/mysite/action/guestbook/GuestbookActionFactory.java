package kr.co.itcen.mysite.action.guestbook;

import kr.co.itcen.mysite.action.main.MainAction;
import kr.co.itcen.mysite.action.user.JoinAction;
import kr.co.itcen.mysite.action.user.JoinFormAction;
import kr.co.itcen.mysite.action.user.JoinSuccessAction;
import kr.co.itcen.mysite.action.user.LoginAction;
import kr.co.itcen.mysite.action.user.LoginFormAction;
import kr.co.itcen.mysite.action.user.LogoutAction;
import kr.co.itcen.mysite.action.user.UpdateAction;
import kr.co.itcen.mysite.action.user.UpdateFormAction;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
	Action action = null;
		
		if("listform".equals(actionName)) {
			action = new ListFormAction();
		} else if("insert".equals(actionName)) {
			action = new InsertAction();
		} else if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else {
			action = new MainAction();
		}
		
		return action;
	}

}
