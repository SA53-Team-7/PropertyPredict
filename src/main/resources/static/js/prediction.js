function determineTenure(t) {
	if (t !== "Freehold") {
		return t
	} else {
		return '999999'
	}
}

function formatCurrency(price) {
	return '$ ' + price.toLocaleString('en-US', {style: 'currency', currency: "SGD"});
}

function determineFloor(floorRange) {
	if (floorRange == "-") {
		return 1
	} else {
		var floor = floorRange.split("-")[0]
		if (floor.charAt(0) == '0') {
			return floor.substring(1)
		} else if (isNaN(floor)) {
			return "0"
		} else {
			return floor
		}
	}
}

function determineDistrict(district) {
	if (district.charAt(0) == '0') {
		return district.substring(1)
	} else {
		return district
	}
}

function predict() {
	var currDate = new Date();
	
	var pid = document.getElementById("pred_pid").value
	var district = determineDistrict(document.getElementById("pred-district").value)
	var floorArea = Number(document.getElementById("pred-area").value)
	var floorRange = determineFloor(document.getElementById("pred-floor").value)
	var top = document.getElementById("pred-top").value
	var tenure = determineTenure(document.getElementById("pred-tenure").value)
	var year = currDate.getFullYear().toString().substring(2)
	var month = (currDate.getMonth() + 1).toString()
	
	var info = {
		"projectId": pid, 
		"district": district,
		"floor_area": floorArea,
		"floor_range": floorRange,
		"top": top,
		"tenure": tenure,
		"year": year,
		"month": month };

	$.ajax({
		type: 'GET',
		url: "https://msdocs-python-webapp-quickstart-te7.azurewebsites.net",
		// data: JSON.stringify(info),
		headers: info,
		contentType: "application/json",
		dataType: 'json',
		success: function(result){
    		price = result[0][0]
    		document.getElementById("price").innerHTML = formatCurrency(price / floorArea);
    		document.getElementById("tot-price").innerHTML = formatCurrency(price);
    		document.getElementById("price-pred").style.display="block";
		}
	})
}	