<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pr="http://www.epam.com/products">

	<xsl:output method="html" />

	<xsl:template name="addGoodForm">
		<html>
			<head>
				<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
				<script type="text/javascript" src="js/cancel.js"></script>
				<link rel="stylesheet" href="css/addGoodForm.css" type="text/css"
					media="screen" />
				<style type="text/css">
					.error-msg {
					color: red;
					}
				</style>
				<title>
					Add good
				</title>
			</head>
			<body>
				<h2>
					Add good in
					<xsl:value-of select="$subcategoryName" />
					subcategory
				</h2>
				<form action="controller">
					<input type="hidden" name="command" value="SAVE_GOOD" />
					<input type="hidden" name="categoryName" value="{$categoryName}" />
					<input type="hidden" name="subcategoryName" value="{$subcategoryName}" />
					<table>
						<tr>
							<td>Producer:</td>
							<td>
								<input type="text" name="producer" value="{$producer}" />
							</td>
							<td class="error-msg">
								<xsl:value-of select="$msgAboutProducer" />
							</td>
						</tr>
						<tr>
							<td>Model(LLNNN, L - letter, N - number):</td>
							<td>
								<input type="text" name="model" value="{$model}" />
							</td>
							<td class="error-msg">
								<xsl:value-of select="$msgAboutModel" />
							</td>
						</tr>
						<tr>
							<td>Date of issue(dd-MM-YYYY):</td>
							<td>
								<input type="text" name="dateOfIssue" value="{$dateOfIssue}" />
							</td>
							<td class="error-msg">
								<xsl:value-of select="$msgAboutDate" />
							</td>
						</tr>
						<tr>
							<td>Color:</td>
							<td>
								<input type="text" name="color" value="{$color}" />
							</td>
							<td class="error-msg">
								<xsl:value-of select="$msgAboutColor" />
							</td>
						</tr>
						<tr>
							<td>Price:</td>
							<td>
								<input type="text" name="price" value="{$price}" />
							</td>
							<td class="error-msg">
								<xsl:value-of select="$msgAboutShopState" />
							</td>
						</tr>
						<tr>
							<td>Not in stock:</td>
							<td>
								<xsl:choose>
									<xsl:when test="$notInStock = 'true'">
										<input type="checkbox" name="notInStock" value="true"
											checked="true" />
									</xsl:when>
									<xsl:otherwise>
										<input type="checkbox" name="notInStock" value="true" />
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</table>
					<br></br>
					<input type="submit" value="SAVE GOOD" />
					<input type="submit" value="CANCEL" />
				</form>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>