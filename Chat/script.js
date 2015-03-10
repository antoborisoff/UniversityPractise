function run(){
	var appContainer = document.getElementsByClassName('wrapper')[0];

	appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {
	if(evtObj.type === 'click' && evtObj.target.classList.contains('indicator')){
		changeIndicator(evtObj);
	}
}

function changeIndicator(){
       document.getElementsByTagName("Circle")[0].setAttribute("fill","green");
} 


