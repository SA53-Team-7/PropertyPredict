function zoomIn() {
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	zoom += 1;
	if (zoom < 20) {
		let newMap = map.substring(0, index + 5) + zoom + map.substring(index + 7);
		document.getElementById("zoom").src = newMap;
	}
}

function zoomOut() {
	let map = document.getElementById("zoom").src;
	let index = map.indexOf("zoom");
	let zoom = parseInt(map.substring(index + 5, index + 7));
	zoom -= 1;
	if (zoom >10) {
		let newMap = map.substring(0, index + 5) + zoom + map.substring(index + 7);
		document.getElementById("zoom").src = newMap;
	}
}