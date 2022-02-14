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
    
    t = setInterval(add, 500);
    
    function add(){
		if(zoom ==11){
			lat += 0.007;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lat += 0.005;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lat += 0.003;	
		}
		else if(zoom == 18 || zoom==19){
			lat += 0.001;	
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
    
    t = setInterval(add, 500);
    
    function add(){
		if(zoom ==11){
			lat -= 0.007;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lat -= 0.005;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lat -= 0.003;	
		}
		else if(zoom == 18 || zoom==19){
			lat -= 0.001;	
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
    
    t = setInterval(add, 500);
    
    function add(){
		if(zoom ==11){
			lng -= 0.007;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lng -= 0.005;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lng -= 0.003;	
		}
		else if(zoom == 18 || zoom==19){
			lng -= 0.001;	
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
    
    t = setInterval(add, 500);
    
    function add(){
		if(zoom ==11){
			lng += 0.007;
		}
		else if(zoom == 12 || zoom==13 || zoom==14){
			lng += 0.005;	
		}
		else if(zoom == 15 || zoom==16 || zoom==17){
			lng += 0.003;	
		}
		else if(zoom == 18 || zoom==19){
			lng += 0.001;	
		}
			
        let newMap = mapStart + lng + mapEnd;
		document.getElementById("zoom").src = newMap;
    };
};

function myStop(){
	clearInterval(t);
};
