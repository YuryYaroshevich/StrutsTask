<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pr="http://www.epam.com/products"
	xmlns:valid="xalan://com.epam.xsl.util.GoodValidator"
	extension-element-prefixes="valid">

	<xsl:param name="validator" />

	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />
	<xsl:param name="producer" />
	<xsl:param name="model" />
	<xsl:param name="dateOfIssue" />
	<xsl:param name="color" />
	<xsl:param name="price" />

	<!-- error messages -->
	<xsl:param name="msgAboutProducer"
		select="valid:validateProducer($validator, $producer)" />
	<xsl:param name="msgAboutModel" select="valid:validateModel($validator, $model)" />
	<xsl:param name="msgAboutDate"
		select="valid:validateDate($validator, $dateOfIssue)" />
	<xsl:param name="msgAboutColor" select="valid:validateColor($validator, $color)" />
	<xsl:param name="msgAboutPrice"
		select="valid:validatePrice($validator, $price)" />

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when
				test="concat($msgAboutProducer, $msgAboutModel, $msgAboutDate, $msgAboutColor, $msgAboutPrice) = ''">
				<xsl:call-template name="saveGood" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="addGoodForm" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:include href="saveGood.xslt" />
	<xsl:include href="addGoodForm.xslt" />

</xsl:stylesheet>