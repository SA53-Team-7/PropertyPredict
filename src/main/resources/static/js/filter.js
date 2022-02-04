/*window.onload = function() {
	var transactionList = "[[${allTxn}]]";
}
*/

var currentDate = new Date();

function filterTable() {	
	var floorDropdown, floorFilter, table, rows, areaDropdown, areaFilter, periodDropdown, periodFilter, txnDate;
	 
	floorDropdown = document.getElementById("floor-dropdown");
	areaDropdown = document.getElementById("area-dropdown");
	periodDropdown = document.getElementById("period-dropdown");
	
	floorFilter = floorDropdown.value;
	areaFilter = areaDropdown.value;
	periodFilter = periodDropdown.value;
	
	var startYear = getStartYear(periodFilter);
	var currMonth = currentDate.getMonth().toString();
	var startPeriodDt = new Date(startYear, currMonth, 15);
	
	table = document.getElementById("property-txn-list")
	rows = table.getElementsByTagName("tr");
	
	for (let row of rows) {
		var col1 = row.getElementsByTagName("td").item(1);
		var floorRange = (col1 === null ? col1 : col1.innerText);
		
		var col2 = row.getElementsByTagName("td").item(2);
		var area = (col2 === null ? col2 : col2.innerText);
		
		var col0 = row.getElementsByTagName("td").item(0);
		
		if (col0 !== null) {
			var month = col0.innerText.substring(0,1) == '0' ? col0.innerText.substring(1,2) : col0.innerText.substring(0,2);
			var year = '20'.concat(col0.innerText.substring(3));		
			txnDate = new Date(year, parseInt(month) - 1, 15);
		}
				
		if (row.rowIndex === 0 || 
			(floorFilter === floorRange || floorFilter === "All") && 
			(areaFilter === area || areaFilter === "All") && 
			(txnDate >= startPeriodDt ||periodFilter == "All")) {
			row.style.display = "";
		} else {
			row.style.display = "none";
		}
	}
}

function getStartYear(period) {
	if (period != "All") {
		var startYear = (currentDate.getFullYear() - period).toString();
		return startYear;
	} 
	return null;	
}


