function run(){
	var appContainer = document.getElementsByClassName('wrapper')[0];

	appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {
	if(evtObj.type == 'click' && evtObj.target.classList.contains('indicator-circle')){
		changeIndicator(evtObj);
	}
	if(evtObj.type == 'click' && evtObj.target.name=='edit'){
		editUserName();
	}
	if(evtObj.type == 'click' && evtObj.target.name=='save'){
		saveUserName();
	}
	if(evtObj.type == 'click' && evtObj.target.name=='send'){
		sendMessage();
	}

	if(evtObj.type == 'click' && evtObj.target.name=='message_history_edit'){
		editInMessageHistory(evtObj.target.parentElement);
	}
	if(evtObj.type == 'click' && evtObj.target.name=='message_history_save'){
		saveInMessageHistory(evtObj.target.parentElement);
	}
	if(evtObj.type == 'click' && evtObj.target.name=='message_history_delete'){
		deleteInMessageHistory(evtObj.target.parentElement);
	}
}

function changeIndicator(){
	var circle=document.getElementsByClassName("indicator-circle")[0];
	if(circle.getAttribute("fill")=="red"){
		circle.setAttribute("fill","green");
		}
		else{
			circle.setAttribute("fill","red");
			}
}

function editUserName(){
	var username_textarea=document.getElementsByClassName("username_textarea")[0];
	username_textarea.removeAttribute("readonly");
}
function saveUserName(){
	var username_textarea=document.getElementsByClassName("username_textarea")[0];
	username_textarea.setAttribute("readonly","true");
}
function sendMessage(){
	var message_textarea=document.getElementsByClassName("message_textarea")[0];
	if(!message_textarea.value){
		return;
	}
	var message_history=document.getElementsByClassName("message_history")[0];
	message_history.appendChild(createNewMyMessage(message_textarea.value));
	message_textarea.value="";
}

function createNewMyMessage(message){
	var divMessage = document.createElement('div');
	divMessage.classList.add('my_message');
	var new_message_textarea = document.createElement('textarea');
	new_message_textarea.setAttribute("cols",50);
	new_message_textarea.setAttribute("rows",5);
	new_message_textarea.setAttribute("readonly","true");
	new_message_textarea.setAttribute("wrap","on");
	new_message_textarea.value=message;
	
	var button_edit=document.createElement('button');
	button_edit.setAttribute("name","message_history_edit");
	button_edit.innerHTML="edit";
	var button_save=document.createElement('button');
	button_save.setAttribute("name","message_history_save");
	button_save.innerHTML="save";
	var button_delete=document.createElement('button');
	button_delete.setAttribute("name","message_history_delete");
	button_delete.innerHTML="delete";
	
	divMessage.appendChild(button_edit);
	divMessage.appendChild(button_save);
	divMessage.appendChild(button_delete);
	divMessage.appendChild(new_message_textarea);
	return divMessage;
}
function createNewNotMyMessage(message){
	var divMessage=document.createElement('div');
	divMessage.classList.add('not_my_message');
	var new_message_textarea = document.createElement('textarea');
	new_message_textarea.setAttribute("cols",50);
	new_message_textarea.setAttribute("rows",5);
	new_message_textarea.setAttribute("readonly","true");
	new_message_textarea.setAttribute("wrap","on");
	divMessage.appendChild(new_message_textarea);
	return divMessage;
}
 
function editInMessageHistory(parentElement){
	var my_message_textarea=parentElement.getElementsByTagName("textarea")[0];
	my_message_textarea.removeAttribute("readonly");
}
function saveInMessageHistory(parentElement){
	var my_message_textarea=parentElement.getElementsByTagName("textarea")[0];
	my_message_textarea.setAttribute("readonly","true");
}
function deleteInMessageHistory(parentElement){
	var message_history=document.getElementsByClassName("message_history")[0];
	message_history.removeChild(parentElement);
}
