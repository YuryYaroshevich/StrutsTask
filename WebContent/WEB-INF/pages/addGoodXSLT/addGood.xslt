<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pr="http://www.epam.com/products">

	<xsl:include href="addGoodForm.xslt" />

	<xsl:output method="html" />

	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />
	<xsl:param name="producer" />
	<xsl:param name="model" />
	<xsl:param name="dateOfIssue" />
	<xsl:param name="color" />
	<xsl:param name="price" />
	<xsl:param name="notInStock" />

	<!-- error messages -->
	<xsl:param name="msgAboutProducer" />
	<xsl:param name="msgAboutModel" />
	<xsl:param name="msgAboutDate" />
	<xsl:param name="msgAboutColor" />
	<xsl:param name="msgAboutShopState" />

	<xsl:template match='/'>
		<xsl:call-template name="addGoodForm" />
	</xsl:template>
</xsl:stylesheet>