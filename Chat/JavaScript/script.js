var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();
	return Math.floor(date * random).toString();
};

var theMessage = function(text, sender,ismymessage) {
	return {
		message:text,
		sender: sender,
		ismymessage:ismymessage,
		id: uniqueId()
	};
};
//////////////////////////////////
var messageHistory = [];
var usernamestorage;
//////////////////////////////////
function storeUsername(username){
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	localStorage.setItem("Username", JSON.stringify(username));
}

function restoreUsername(){
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var username = localStorage.getItem("Username");
	return username && JSON.parse(username);
}
//////////////////////////////////
function run(){
	var appContainer = document.getElementsByClassName('wrapper')[0];

	appContainer.addEventListener('click', delegateEvent);
	var allMessages = restoreMessageHistory() || [];
	createAllMessages(allMessages);

	var username_textarea=document.getElementsByClassName("username_textarea")[0];
	usernamestorage=restoreUsername();
	username_textarea.value=usernamestorage;
}

function createAllMessages(allMessages) {
	var message;
	var message_history=document.getElementsByClassName("message_history")[0];
	for(var i = 0; i < allMessages.length; i++){
		if(allMessages[i].ismymessage){
			message=createNewMyMessage(allMessages[i].message,allMessages[i].sender);
		}
		else{
			message=createNewNotMyMessage(allMessages[i].message,allMessages[i].sender);
			}
		messageHistory.push(allMessages[i]);
		message.setAttribute("id",allMessages[i].id);
		message_history.appendChild(message);
	}
}
//////////////////////////////////
function restoreMessageHistory() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var item = localStorage.getItem("MessageHistory");
	return item && JSON.parse(item);
}

function storeMessageHistory(listToSave) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	localStorage.setItem("MessageHistory", JSON.stringify(listToSave));
}
//////////////////////////////////
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
//////////////////////////////////
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

	usernamestorage=username_textarea.value;
	storeUsername(usernamestorage);
}
//////////////////////////////////
function sendMessage(){
	var message_textarea=document.getElementsByClassName("message_textarea")[0];
	if(!message_textarea.value){
		return;
	}
	var message_history=document.getElementsByClassName("message_history")[0];
	var message_text=message_textarea.value;
	var new_my_message=createNewMyMessage(message_text,null);
	
	var username=document.getElementsByClassName("username_textarea")[0].value;
	messageHistory.push(theMessage(message_text,username,true));
	var message=messageHistory.pop();
	new_my_message.setAttribute("id",message.id);
	messageHistory.push(message);
	
	message_history.appendChild(new_my_message);
	message_textarea.value="";
	storeMessageHistory(messageHistory);
}
//////////////////////////////////
function createNewMyMessage(message,sender){
	var divMessage = document.createElement('div');
	divMessage.classList.add('my_message');
	var new_message_textarea = document.createElement('textarea');
	new_message_textarea.classList.add("text");
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

	var sender_textarea=document.createElement('textarea');
	sender_textarea.setAttribute("name","sender_textarea");
	sender_textarea.setAttribute("cols",25);
	sender_textarea.setAttribute("rows",1);
	sender_textarea.setAttribute("readonly","true");
	if(sender==null){
		var username_textarea=document.getElementsByClassName("username_textarea")[0];
		sender_textarea.value=username_textarea.value;
	}
	else{
		sender_textarea.value=sender;
	}

	divMessage.appendChild(sender_textarea);
	divMessage.appendChild(button_edit);
	divMessage.appendChild(button_save);
	divMessage.appendChild(button_delete);
	divMessage.appendChild(new_message_textarea);
	return divMessage;
}
function createNewNotMyMessage(message,sender){
	var divMessage=document.createElement('div');
	divMessage.classList.add('not_my_message');
	var new_message_textarea = document.createElement('textarea');
	new_message_textarea.classList.add("text");
	new_message_textarea.setAttribute("cols",50);
	new_message_textarea.setAttribute("rows",5);
	new_message_textarea.setAttribute("readonly","true");
	new_message_textarea.setAttribute("wrap","on");

	var sender_textarea=document.createElement('textarea');
	sender_textarea.setAttribute("name","sender_textarea");
	sender_textarea.setAttribute("cols",25);
	sender_textarea.setAttribute("rows",1);
	sender_textarea.setAttribute("readonly","true");
	if(sender==null){
		sender_textarea.value="Interlocutor";
	}
	else{
		sender_textarea.value=sender;
	}

	divMessage.appendChild(sender_textarea);
	divMessage.appendChild(new_message_textarea);
	return divMessage;
}
 /////////////////////////////////
function editInMessageHistory(parentElement){
	var my_message_textarea=parentElement.getElementsByClassName("text")[0];
	my_message_textarea.removeAttribute("readonly");
}
function saveInMessageHistory(parentElement){
	var my_message_textarea=parentElement.getElementsByClassName("text")[0];
	my_message_textarea.setAttribute("readonly","true");

	var id=parentElement.id;
	var i=0;
	var found=false;
	while(i<messageHistory.length&&!found){
		if(messageHistory[i].id==id){
			found=true;
		}
		else{
			i=i+1;
		}
	}
	messageHistory[i].message=my_message_textarea.value;
	storeMessageHistory(messageHistory);
}
function deleteInMessageHistory(parentElement){
	var message_history=document.getElementsByClassName("message_history")[0];
	message_history.removeChild(parentElement);

	var id=parentElement.id;
	var i=0;
	var found=false;
	while(i<messageHistory.length&&!found){
		if(messageHistory[i].id==id){
			found=true;
		}
		else{
			i=i+1;
		}
	}
	messageHistory.splice(i,1);
	storeMessageHistory(messageHistory);
}

