/*window.onload = function() {
	var transactionList = "[[${allTxn}]]";
}
*/

function filterTable() {	
	var floorDropdown, floorFilter, table, rows, areaDropdown, areaFilter, periodDropdown, periodFilter;
	var currentDate = new Date().toString();
	console.log(currentDate);
	 
	floorDropdown = document.getElementById("floor-dropdown");
	areaDropdown = document.getElementById("area-dropdown");
	periodDropdown = document.getElementById("period-dropdown");
	
	floorFilter = floorDropdown.value;
	areaFilter = areaDropdown.value;
	periodFilter = periodDropdown.value;
	
	table = document.getElementById("property-txn-list")
	rows = table.getElementsByTagName("tr");
	
	for (let row of rows) {
		var col1 = row.getElementsByTagName("td").item(1);
		var floorRange = (col1 === null ? col1 : col1.innerText);
		
		var col2 = row.getElementsByTagName("td").item(2);
		var area = (col2 === null ? col2 : col2.innerText);
		
		if (row.rowIndex === 0 || 
			(floorFilter === floorRange || floorFilter === "All") && 
			(areaFilter === area || areaFilter === "All")) {
			row.style.display = "";
		} else {
			row.style.display = "none";
		}
	}
	
}

	/*var table, rows;
	var floorDropdown = document.getElementById("floor-dropdown");
	var selectedFloor = floorDropdown.options[floorDropdown.selectedIndex].value;
	
	table = document.getElementById("property-txn-list");
	rows = table.getElementsByTagName("tr");
	
	for (let row of rows) {
		cols = row.getElementsByTagName("td");
		var floor = cols[1];
		
		console.log(floor.value);*/
		
		
		/*if (floor.textContext === selectedFloor) {
			row.style.display = "";
		} else {
			row.style.display = "none";
		}*/
	//}
	
/*	var floorSelect, table, rows, cols, filter, floorRange;
	floorSelect = document.getElementById("floor-dropdown");
	table = document.getElementById("property-txn-list");
	rows = table.getElementById("tr");
	filter = floorSelect.value;
	
	for (let row of rows) {
		cols = row.getElementByTagName("td");
		floorRange = cols[1] || null;
		
		if (filter === floorRange.textContent) {
			row.style.display = "";
		} else {
			row.style.display = "none";
		}
	}*/
