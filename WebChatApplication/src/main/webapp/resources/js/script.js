var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();
	return Math.floor(date * random).toString();
};

var theMessage = function(text, sender,idClient,isDeleteAction) {
	return {
		id: uniqueId(),
		message:text,
		username: sender,
		idClient:idClient,
        isDeleteAction:isDeleteAction
	};
};
//////////////////////////////////
var appState = {
	mainUrl : 'chat',
	messageHistory:[],
	token : 'TN11D11EN',
	idClient:uniqueId(),
	usernamestorage:'User'
};

//////////////////////////////////
function loop(ContinueWith){
	restoreMessageHistory();
	setTimeout(function(){
loop(ContinueWith)},1000);
}
function run(){
	
	var appContainer = document.getElementsByClassName('wrapper')[0];
	appContainer.addEventListener('click', delegateEvent);
	loop();
}
//////////////////////////////////
function createAllMessages(allMessages) {
	var message;
	var message_history=document.getElementsByClassName("message_history")[0];
	for(var i = 0; i < allMessages.length; i++){
		if(allMessages[i].idClient==appState.idClient){
			message=createNewMyMessage(allMessages[i].message,allMessages[i].username);
		}
		else{
			message=createNewNotMyMessage(allMessages[i].message,allMessages[i].username);
		}
		appState.messageHistory.push(allMessages[i]);
		message.setAttribute("id",allMessages[i].id);
		message_history.appendChild(message);
	}
}

function restoreMessageHistory(continueWith) {
	var url = appState.mainUrl + '?token=' + appState.token;

	get(url,function(responseText){
		setIndicatorGreen();
		output("Connected.");

		var response = JSON.parse(responseText);
		appState.token=response.token;
		var messagestoadd=doPutDeleteList(appState.messageHistory,response.messages,response.putdeletelist);
		createAllMessages(messagestoadd);

		continueWith && continueWith();
	});
}

function findMessageIndex(messages,message){
	var id=message.id;
	var i=0;
	var found=false;
	while(i<messages.length&&!found){
		if(messages[i].id==id){
			     found=true;
		 }
		 else{
			     i=i+1;
		 }
	}
	return i;
}

function doPutDeleteList(messageHistory,messagesforaddition,putdeletelist){
	var message_history=document.getElementsByClassName('message_history')[0];
	var listofmessagelements=message_history.children;
	var messageindex;
	var messagestoadd=messagesforaddition;
	for(var j=0;j<putdeletelist.length;j++){
		messageindex=findMessageIndex(appState.messageHistory,putdeletelist[j]);
	    if(messageindex==appState.messageHistory.length){
	    	messageindex=findMessageIndex(messagestoadd,putdeletelist[j]);
	    	if(messageindex!=messagestoadd.length){
	    		if(putdeletelist[j].isDeleteAction=="true"){
	               messagestoadd.splice(messageindex,1);
		        }
		        else{
	               messagestoadd[messageindex].message=putdeletelist[j].message;
		        }
	    	}
	    }
	    else{
		   if(putdeletelist[j].isDeleteAction=="true"){
	            appState.messageHistory.splice(messageindex,1);
	            message_history.removeChild( listofmessagelements[messageindex].getElementsByTagName('textarea')[0].parentNode);
		   }
		   else{
	            appState.messageHistory[messageindex].message=putdeletelist[j].message;
	             listofmessagelements[messageindex].getElementsByClassName('text')[0].value=putdeletelist[j].message;
		   }
	    }
	}
	return messagestoadd;
}

//////////////////////////////////
function delegateEvent(evtObj) {
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
function setIndicatorRed(){
	var circle=document.getElementsByClassName("indicator-circle")[0];
	circle.setAttribute("fill","red");
}

function setIndicatorGreen(){
	var circle=document.getElementsByClassName("indicator-circle")[0];
	circle.setAttribute("fill","green");
}
//////////////////////////////////
function editUserName(){
	var username_textarea=document.getElementsByClassName("username_textarea")[0];
	username_textarea.removeAttribute("readonly");
}

function saveUserName(){
	var username_textarea=document.getElementsByClassName("username_textarea")[0];
	username_textarea.setAttribute("readonly","true");

	appState.usernamestorage=username_textarea.value;
}
//////////////////////////////////
function sendMessage(){
	var message_textarea=document.getElementsByClassName("message_textarea")[0];
	if(!message_textarea.value){
		return;
	}
	var message_text=message_textarea.value;
	
	var username=document.getElementsByClassName("username_textarea")[0].value;
	var message=theMessage(message_text,username,appState.idClient,"false");

	message_textarea.value="";
	post(appState.mainUrl,JSON.stringify(message),function(){
		setIndicatorGreen();
		output("Connected.");

		//restoreMessageHistory();
		});
}
//////////////////////////////////
function createNewMyMessage(message,sender){
	var divMessage = document.createElement('div');
	divMessage.classList.add('my_message');
	var new_message_textarea = document.createElement('textarea');
	new_message_textarea.classList.add("text");
	new_message_textarea.setAttribute("cols",135);
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
	new_message_textarea.setAttribute("cols",135);
	new_message_textarea.setAttribute("rows",5);
	new_message_textarea.setAttribute("readonly","true");
	new_message_textarea.setAttribute("wrap","on");
	new_message_textarea.value=message;

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

    var username=document.getElementsByClassName("username_textarea")[0].value;
	var message=theMessage(my_message_textarea.value,username,appState.idClient,"false");
	message.id=parentElement.id;

	put(appState.mainUrl,JSON.stringify(message),function(){
		setIndicatorGreen();
		output("Connected.");

		//restoreMessageHistory();
	});
}

function deleteInMessageHistory(parentElement){
	var message_history=document.getElementsByClassName("message_history")[0];

    var my_message_textarea=parentElement.getElementsByClassName("text")[0];

	var message=theMessage(my_message_textarea.value,appState.usernamestorage,appState.idClient,"true");
	message.id=parentElement.id;

	del(appState.mainUrl,JSON.stringify(message),function(){
		setIndicatorGreen();
		output("Connected.");

		//restoreMessageHistory();
	});
}
/////////////////////////////////////////////////
function output(value){
	var output = document.getElementById('output');

	output.innerText = JSON.stringify(value, null, 2);
}
//////////////////////////////////////////////////
function defaultErrorHandler(message) {
	setIndicatorRed();
	console.error(message);
	output(message);
}

function isError(text) {
	if(text == "")
		return false;
	
	try {
		var obj = JSON.parse(text);
	} catch(ex) {
		return true;
	}

	return !!obj.error;
}

window.onerror = function(err) {
	output(err.toString());
}
//////////////////////////////////////////////////
function get(url, continueWith, continueWithError) {
	ajax('GET', url, appState.token, continueWith, continueWithError);
}

function post(url, data, continueWith, continueWithError) {
	ajax('POST', url, data, continueWith, continueWithError);	
}

function put(url, data, continueWith, continueWithError) {
	ajax('PUT', url, data, continueWith, continueWithError);	
}

function del(url, data, continueWith, continueWithError) {
	ajax('DELETE', url, data, continueWith, continueWithError);	
}

function ajax(method, url, data, continueWith, continueWithError) {
	var xhr = new XMLHttpRequest();

	continueWithError = continueWithError || defaultErrorHandler;
	xhr.open(method || 'GET', url, true);

	xhr.onload = function () {
		if (xhr.readyState !== 4)
			return;

		if(xhr.status != 200) {
			continueWithError('Error on the server side, response ' + xhr.status);
			return;
		}

		if(isError(xhr.responseText)) {
			continueWithError('Error on the server side, response ' + xhr.responseText);
			return;
		}
		
		continueWith(xhr.responseText);
	};    

    xhr.ontimeout = function () {
    	ContinueWithError('Server timed out !');
    }

    xhr.onerror = function (e) {
    	var errMsg = 'Server connection error !\n'+
    	'\n' +
    	'Check if \n'+
    	'- server is active\n'+
    	'- server sends header "Access-Control-Allow-Origin:*"';

        continueWithError(errMsg);
    }; 
    xhr.send(data);
}


