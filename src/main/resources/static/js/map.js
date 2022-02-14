function zoomIn() {
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	zoom += 1;
	if (zoom < 20) {
		let newMap = map.substring(0, index + 5) + zoom + map.substring(index + 7);
		document.getElementById("zoom").src = newMap;
	};
};

function zoomOut() {
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	zoom -= 1;
	if (zoom >10) {
		let newMap = map.substring(0, index + 5) + zoom + map.substring(index + 7);
		document.getElementById("zoom").src = newMap;
	};
};

function up(){
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	
	let start = map.indexOf("lat");
	let latStart = map.substring(start + 4);
    let end = latStart.indexOf("&");
    let lat = parseFloat(latStart.substring(0,end));
    
    let mapStart = map.substring(0, start+4);
    let mapEnd = latStart.substring(end);
    
    t = setInterval(add, 200);
    
    function add(){
		if(zoom ==11){
			lat += 0.003;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lat += 0.002;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lat += 0.001;	
		}
		else if(zoom == 18 || zoom==19){
			lat += 0.0005;	
		}
			
        let newMap = mapStart + lat + mapEnd;
		document.getElementById("zoom").src = newMap;
    };
};

function down(){
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	
	let start = map.indexOf("lat");
	let latStart = map.substring(start + 4);
    let end = latStart.indexOf("&");
    let lat = parseFloat(latStart.substring(0,end));
    
    let mapStart = map.substring(0, start+4);
    let mapEnd = latStart.substring(end);
    
    t = setInterval(add, 200);
    
    function add(){
		if(zoom ==11){
			lat -= 0.003;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lat -= 0.002;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lat -= 0.001;	
		}
		else if(zoom == 18 || zoom==19){
			lat -= 0.0005;	
		}
			
        let newMap = mapStart + lat + mapEnd;
		document.getElementById("zoom").src = newMap;
    };
};

function left(){
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	
	let start = map.indexOf("lng");
	let lngStart = map.substring(start + 4);
    let end = lngStart.indexOf("&");
    let lng = parseFloat(lngStart.substring(0,end));
    
    let mapStart = map.substring(0, start+4);
    let mapEnd = lngStart.substring(end);
    
    t = setInterval(add, 200);
    
    function add(){
		if(zoom ==11){
			lng -= 0.003;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lng -= 0.002;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lng -= 0.001;	
		}
		else if(zoom == 18 || zoom==19){
			lng -= 0.0005;	
		}
			
        let newMap = mapStart + lng + mapEnd;
		document.getElementById("zoom").src = newMap;
    };
};

function right(){
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	
	let start = map.indexOf("lng");
	let lngStart = map.substring(start + 4);
    let end = lngStart.indexOf("&");
    let lng = parseFloat(lngStart.substring(0,end));
    
    let mapStart = map.substring(0, start+4);
    let mapEnd = lngStart.substring(end);
    
    t = setInterval(add, 200);
    
    function add(){
		if(zoom ==11){
			lng += 0.003;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lng += 0.002;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lng += 0.001;	
		}
		else if(zoom == 18 || zoom==19){
			lng += 0.0005;	
		}
			
        let newMap = mapStart + lng + mapEnd;
		document.getElementById("zoom").src = newMap;
    };
};

function myStop(){
	clearInterval(t);
};

// locations event listener
let elems = document.getElementsByClassName("mark");

for(let i=0; i<elems.length; i++){
	let elem = elem[i];
	elem.addEventListener('click', onClick);
}

function onClick(event){
	let elem = event.currentTarget;
	elem.style.color = "red";
}

function changeLoc(loc){
	let map = document.getElementById("all").src;
	
	let start1 = map.indexOf("lat");
	let latStart = map.substring(start1 + 4);
    let end1 = latStart.indexOf("&");
    let lat = parseFloat(latStart.substring(0,end1));
    
    let start2 = map.indexOf("lng");
	let lngStart = map.substring(start2 + 4);
    let end2 = lngStart.indexOf("&");
    let lng = parseFloat(lngStart.substring(0,end2));
    
    let start3 = map.indexOf("P");
    let mapStart = map.substring(0, start3 + 1);
    let start4 = map.indexOf(loc);
    let locNext = map.substring(start4-1);
    let next = locNext.indexOf("]");
    let mapNext = locNext.substring(0, next+1);
    
    let newMap = mapStart + "\"]|" + mapNext + "&lines=[[" + lat + "," + lng + "],[" + loc + "]]:177,0,0:3";

	
	document.getElementById("zoom").src = newMap;
}

function showAll(){
	let map = document.getElementById("all").src;
	document.getElementById("zoom").src = map;
}


