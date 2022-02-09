function filterResults() {
	
	var propTypeArr = []
	var tenureArr = []
	
	// Get all checkboxes for property types
	var propTypeElements = document.getElementsByClassName("propertyType")
	
	// Get all checkboxes for tenure
	var tenureElements = document.getElementsByClassName("tenure")
	
	// Get selected property types by user 
	for (i = 0; i < propTypeElements.length; i++) {
		if (propTypeElements[i].checked === true) {
			propTypeArr.push(propTypeElements[i].value)
		}
	}	
	
	// Get selected tenure by user 
	for (i = 0; i < tenureElements.length; i++) {
		if (tenureElements[i].checked === true) {
			tenureArr.push(tenureElements[i].value)
		}
	}
	
	// Get all display cards	
	var displayElements = document.getElementsByClassName("result-row")
		
	for (i = 0; i < displayElements.length; i++) {
		var tdContent = displayElements.item(i).childNodes.item(1)
		var type = tdContent.getElementsByClassName("type-text").item(0).innerHTML.substring(6)
		var tenure = tdContent.getElementsByClassName("tenure-text").item(0).innerHTML.substring(8)
		var typeExist = false
		
		for (j = 0; j < propTypeArr.length; j++) {
			if (type.includes(propTypeArr[j])) {
				typeExist = true;
				break;
			}
		}
		
		if (!typeExist || !tenureArr.includes(tenure,0)) {
			tdContent.style.display = "none"
		} else {
			tdContent.style.display = "block"
		}
	}	
}