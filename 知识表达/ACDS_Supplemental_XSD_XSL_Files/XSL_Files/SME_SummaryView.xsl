<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
<!--<xsl:import href="ui.xsl"/>-->
	<xsl:output method="html" indent="yes"/>
	<!--
	CREATED: 4/19/2011 Y. Vulfovich yvulfovich@partners.org
	-->
	<xsl:variable name="lcletters">abcdefghijklmnopqrstuvwxyz</xsl:variable>
	<xsl:variable name="ucletters">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>
	
	<xsl:template match="StructuredCDSKnowledge|StructuredGuideline|structuredCDSKnowledge|structuredGuideline">
		<style>
			.divider {height:2px;background-color:blue;width:100%;}
			.heading {font-weight:bold;font-size:13px;font-family:Arial;align:center;}
			td.heading {background-color: #F2F3F4; margin-bottom: 2px;
			  font-weight:bold;font-size:13px;font-family:Arial;}
			.content {font-size:12;font-family:Arial;align:right;font-weight:normal;}
			.headingContent {font-size:12;font-family:Arial;align:right;font-weight:normal;}
			td.headingContent{background-color: #F2F3F4; margin-bottom: 2px;
			  font-weight:normal;font-size:12px;font-family:Arial;}
			.field {font-size:12;font-family:Arial;font-weight:normal;}
			
			.fieldHeader {font-size:12;font-family:Arial;font-weight:bold;}
			.checkMark {font-size:13;font-family:Webdings;font-weight:bold;}
			.headerTitle {font-size:14;font-family:Arial;font-weight:bold;}
			#page,table {background: white}
			.fieldLink {font-size:12;font-family:Arial;color:blue;cursor:hand;text-decoration:underline;}
			.fieldTitle {font-size:13px;font-family:Arial;}

		</style>
		
		<p align="center" class=".headerTitle">Subject Matter Expert Style Sheet 
		</p>
		<table width="100%">
			<tr>
				<td class="divider"/>
			</tr>
			<tr>
				<td class="heading">
					<xsl:value-of select="Metadata/identity/description"/>
					<br/>
				</td>
			</tr>
			<tr>
				<td class="divider"/>
			</tr>
		</table>
		<xsl:if test="Metadata/contributor/organization/name!='' or Metadata/implementation/knowledgeType!=''">
			<table width="100%">
				<xsl:if test="Metadata/contributor/organization/name!=''">
					<tr>
						<td width="12%" class="fieldTitle" valign="top"><b>Contributor:</b></td>
						<td width="88%" class="field" valign="top"><xsl:value-of select="Metadata/contributor/organization/name" /></td>
					</tr>
				</xsl:if>
				<xsl:if test="Metadata/implementation/knowledgeType">
					<tr>
						<td width="12%" class="fieldTitle" valign="top"><b>Knowledge Type:</b></td>
						<td width="88%" class="field" valign="top"><xsl:value-of select="Metadata/implementation/knowledgeType" /></td>
					</tr>
				</xsl:if>
				<xsl:if test="Metadata/stateChangeEvent/eventCode">
					<tr>
						<td width="12%" class="fieldTitle" valign="top"><b>Change Event:</b></td>
						<td width="88%" class="field" valign="top">
							<xsl:value-of select="Metadata/stateChangeEvent/eventCode" />,
							<xsl:value-of select="Metadata/stateChangeEvent/eventDateTime" />
						</td>
					</tr>
				</xsl:if>
			</table>
			<br />
		</xsl:if>
		
		<xsl:if test="Metadata/identity">
			<xsl:apply-templates select="Metadata/identity" />
			<br />
		</xsl:if>
			
		<xsl:if test="Definition">
			<xsl:apply-templates select="Definition"/>
			<br/>
		</xsl:if>
		
		<xsl:if test="ApplicableScenario">
			<xsl:for-each select="ApplicableScenario">
				<xsl:choose>
					<xsl:when test="LogicalConditionSet">
						<xsl:apply-templates select="LogicalConditionSet" />
					</xsl:when>
					<xsl:when test="LogicalConditionSingle">
						<xsl:apply-templates select="LogicalConditionSingle"/>
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>
			<br/>
		</xsl:if>
		
		<xsl:if test="module">
			<xsl:for-each select="module">
				<xsl:if test="module/Metadata/identity/title != ''">
					<table width="100%">
						<tr>
							<td class="divider"/>
						</tr>
						<tr>
							<td class="heading">
								<b><xsl:value-of select="Metadata/identity/title"/></b>
								<br/>
								<span class="headingContent">
									<xsl:value-of select="Metadata/identity/description"/>
									<!-- new code for relatedResource -->
									<xsl:if test="Metadata/identity">
										<xsl:apply-templates select="Metadata/identity" />
									</xsl:if>
									<br/>
								</span>
							</td>
						</tr>
						<tr>
							<td class="divider"/>
						</tr>
					</table>
				</xsl:if>
				<xsl:if test="recommendation">
					<xsl:for-each select="recommendation">
						<xsl:if test="Metadata/identity/title!=''">
							<span class="fieldTitle">
								<b><xsl:value-of select="Metadata/identity/title"/></b>
							</span>
							<!--<br/>-->
						</xsl:if>
						<xsl:if test="Metadata/identity/description!=''">
							<span class="field">
								<xsl:value-of select="Metadata/identity/description"/>
							</span>
							<!-- new code for relatedResource -->
							<xsl:if test="Metadata/identity">
								<xsl:apply-templates select="Metadata/identity" />
							</xsl:if>
							<br/>
						</xsl:if>
						<xsl:if test="ApplicableScenario">
							<xsl:apply-templates select="ApplicableScenario" />
						</xsl:if>
						<xsl:apply-templates select="Advice"/>
						<xsl:if test="position() != last()">
							<br/>
							<table width="50%">
								<tr>
									<td class="divider"/>
								</tr>
							</table>
						</xsl:if>
					</xsl:for-each>
					<br/>
				</xsl:if>
			</xsl:for-each>
			<br/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="Metadata/identity">
		<xsl:if test="relatedResource">
			<span class="heading">Related Resources:</span>
			<table width="100%" cellspacing="2">
				<xsl:for-each select="relatedResource">
					<xsl:call-template name="relatedResource" />
					<tr>
						<td colspan="2" height="2px" class="field"></td>
					</tr>
				</xsl:for-each>
			</table>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="relatedResource">
		<tr>
			<td width="11%" class="field" valign="top">
				<xsl:if test="type!=''"><xsl:value-of select="type" />:&#160;</xsl:if>
			</td>
			<td width="89%" class="field" valign="top">
				<xsl:choose>
					<xsl:when test="resource/description!=''">
						<xsl:value-of select="resource/description" />
					</xsl:when>
					<xsl:otherwise><xsl:value-of  select="resource/title" /></xsl:otherwise>
				</xsl:choose>
				<xsl:if test="resource/location!=''">:&#160;<span class="fieldLink">
					<a target='_blank'><xsl:attribute name="href"><xsl:value-of select="resource/location" /></xsl:attribute><xsl:value-of select="resource/location" /></a>
				</span></xsl:if>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template match="Advice">
		<!--<br/><br />-->
		<xsl:if test="title!=''">
			<span class="fieldTitle"><b><xsl:value-of select="title" /></b></span>
	  </xsl:if>
		<xsl:apply-templates select="ActionConcrete"/>
	</xsl:template>
	<xsl:template match="ActionConcrete">
		<xsl:apply-templates select="ActionOrganizer"/>
	</xsl:template>
	<xsl:template match="ActionOrganizer">
		<xsl:param name="operatorCode" select="operator/@code"/>
		<xsl:param name="testKnowledge" select="ancestor::*/ancestor::*/ancestor::*/ancestor::*/ancestor::*/ancestor::*/Metadata/implementation/knowledgeType" />
		<table width="100%" cellspacing="0">
			<xsl:if test="ActionConcrete/ObservationRequest and ancestor::*/ancestor::*/Metadata/implementation/knowledgeType='Documentation Template/Form'">
				<tr>
					<td width="12%" valign="top" class="fieldHeader">
						Observation Request:&#160;
					</td>
					<td width="8%" class="fieldHeader" valign="top"><i>Check if Yes</i></td>
					<td width="65%" class="fieldHeader" valign="top"><i>Observation</i></td>
					<td width="15%" class="fieldHeader" valign="top"><i>Observation Value</i></td>
				</tr>
			</xsl:if>
			<xsl:for-each select="ActionConcrete">
				<xsl:sort select="EncounterRequest or EventRequest or eventRequest or KnowledgeAssetRequest or MessageRequest or ProcedureRequest or SubstanceAdministrationRequest or SupplyRequest"  />
				<xsl:choose>
					<xsl:when test="ActionOrganizer">
						<xsl:apply-templates select="ActionOrganizer"/>
					</xsl:when>
					<xsl:when test="MessageRequest">
						<tr>
							<td colspan="4" class="field">
								<xsl:apply-templates select="MessageRequest"/>
							</td>
						</tr>
					</xsl:when>
					<xsl:when test="ObservationRequest">
						<xsl:choose>
							<xsl:when test="$testKnowledge='Documentation Template/Form'">
								<xsl:apply-templates select="ObservationRequest" />
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<td colspan="4"  class="field">
										<xsl:apply-templates select="ObservationRequest" />
										<span class="field">
											<xsl:if test="position() != last() and $testKnowledge!='Documentation Template/Form'">&#160;&#160;
												<xsl:choose>
													<xsl:when test="$operatorCode='XOR'"><b>OR</b></xsl:when>
													<xsl:when test="$operatorCode='AND'"><b>AND</b></xsl:when>
													<xsl:otherwise>&#160;
														<xsl:if test="$operatorCode='OR'">
															<xsl:if test="ancestor::*/ancestor::*/ancestor::*/ancestor::*/ActionOrganizer">
																&#160;&#160;<b>OR</b>
															</xsl:if>
														</xsl:if>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:if>
										</span>
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<tr>
							<td colspan="4" class="field">
								<xsl:apply-templates select="ClinicalStateRequest"/>
								<xsl:apply-templates select="EncounterRequest"/>
								<xsl:apply-templates select="EventRequest|eventRequest"/>
								<xsl:apply-templates select="KnowledgeAssetRequest"/>
								<xsl:apply-templates select="ProcedureRequest"/>
								<xsl:apply-templates select="SubstanceAdministrationRequest"/>
								<xsl:apply-templates select="SupplyRequest"/>
								<xsl:if test="not(ObservationRequest) and not(MessageRequest)">
									<span class="field">
										<xsl:if test="position() != last()">&#160;&#160;
											<xsl:choose>
												<xsl:when test="$operatorCode='XOR'"><b>OR</b></xsl:when>
												<xsl:when test="$operatorCode='AND'"><b>AND</b></xsl:when>
												<xsl:otherwise>&#160;
													<xsl:if test="$operatorCode='OR'">
														<xsl:if test="ancestor::*/ancestor::*/ancestor::*/ancestor::*/ActionOrganizer">
															&#160;&#160;<b>OR</b>
														</xsl:if>
													</xsl:if>
												</xsl:otherwise>
											</xsl:choose>
										</xsl:if>
									</span>
								</xsl:if>
								<br/>
							</td>
						</tr>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</table>
	</xsl:template>
	<xsl:template match="EncounterRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Encounter Request'" />
			<xsl:with-param name="hoverText" select="encounterProvider/@displayName"/>
			<xsl:with-param name="hoverCode" select="encounterProvider/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="encounterProvider/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="KnowledgeAssetRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Knowledge Asset Request'" />
			<xsl:with-param name="hoverText" select="resource/knowledgeResourceType/@displayName"/>
			<xsl:with-param name="hoverCode" select="resource/knowledgeResourceType/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="resource/knowledgeResourceType/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="resource/title" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="resource/location" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="MessageRequest">
		<xsl:param name="requestName" select="'Message'" />
		<xsl:param name="hoverText" select="target/@displayName"/>
		<xsl:param name="hoverCode" select="target/@code"/>
		<xsl:param name="hoverCodeSystemName" select="target/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
		
		<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="field">
					<b>
						<xsl:choose>
							<xsl:when test="$hoverCode='309343006' or $hoverCode='Physician'">Physician Message:</xsl:when>
							<xsl:when test="$hoverCode='116154003'">Patient Message:</xsl:when>
							<xsl:when test="$hoverCode='125677006' or $hoverCode='Parent'">Relative Message:</xsl:when>
							<xsl:otherwise>Display Message:</xsl:otherwise>
						</xsl:choose>
					</b>
				</td>
			</tr>
			<tr>
				<td class="field">
					<xsl:value-of select="Message/freeTextMessage/@value"/>
				</td>
			</tr>
		</table>
	</xsl:template>
	
	<xsl:template match="MessageRequestNew">
		<xsl:param name="requestName" select="'Message'" />
		<xsl:param name="hoverText" select="target/@displayName"/>
		<xsl:param name="hoverCode" select="target/@code"/>
		<xsl:param name="hoverCodeSystemName" select="target/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
			<b>
				<xsl:choose>
					<xsl:when test="$hoverCode='309343006' or $hoverCode='Physician'">Physician Message:&#160;&#160;</xsl:when>
					<xsl:when test="$hoverCode='116154003'">Patient Message:&#160;&#160;</xsl:when>
					<xsl:when test="$hoverCode='125677006' or $hoverCode='Parent'">Relative Message:&#160;&#160;</xsl:when>
					<xsl:otherwise>Display Message:&#160;&#160;</xsl:otherwise>
				</xsl:choose>
			</b>	
			<br />
			<xsl:value-of select="Message/freeTextMessage/@value"/><br />
	</xsl:template>
		
	<xsl:template match="ObservationRequest">
		<xsl:param name="requestName" select="'Observation Request'" />
		<xsl:param name="hoverText" select="observationCode/@displayName"/>
		<xsl:param name="hoverCode" select="observationCode/@code"/>
		<xsl:param name="hoverCodeSystemName" select="observationCode/@codeSystemName"/>
		<xsl:param name="ancestorCode" select="ancestor::*/operator/@code" />
		<xsl:param name="selectionCriteria" select="selectionCriteria" />
		<xsl:param name="title" select="''" />
		<xsl:param name="textAlternative" select="textAlternative" />
		<xsl:param name="location" select="''" />
		<xsl:param name="targetDisplayName" select="target/@displayName" />
		<xsl:param name="targetCode" select="target/@code" />
		<xsl:param name="targetCodeSystemName" select="target/@codeSystemName" />
		<xsl:param name="testKnowledge" select="ancestor::*/ancestor::*/Metadata/implementation/knowledgeType" />
		<xsl:choose>
			<xsl:when test="$testKnowledge='Documentation Template/Form'">
				<tr>
					<td width="12%">&#160;</td>
					<td class="field" valign="top" width="8%">
						<xsl:element name="input">
							<xsl:attribute name="type">checkbox</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="$hoverText"/></xsl:attribute>
							<xsl:if test="$selectionCriteria='recommended'">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
						</xsl:element>
					</td>
					<td class="field" width="65%">
						<xsl:choose>
							<xsl:when test="textAlternative!=''">
								<xsl:value-of select="textAlternative"/>&#160;
								<xsl:if test="$hoverText!=''">-&#160;<xsl:value-of select="$hoverText" />&#160;</xsl:if>
							</xsl:when>
							<xsl:otherwise><xsl:value-of select="$hoverText"/></xsl:otherwise>
						</xsl:choose>
						&#160;
						(<xsl:value-of select="$hoverCodeSystemName"/>:&#160;&#160;<xsl:value-of select="$hoverCode"/>)
						<xsl:if test="$targetDisplayName!=''">
							&#160;&#160;<i><b>Target:</b></i>&#160;<xsl:value-of select="$targetDisplayName" />&#160;(<xsl:value-of select="$targetCodeSystemName" />:&#160;&#160;<xsl:value-of select="$targetCode" />)
						</xsl:if>
						<xsl:if test="$location!=''">&#160;&#160;&#160;&#160;&#160;&#160;&#160;<span class="fieldLink">
							<a target='_blank'><xsl:attribute name="href"><xsl:value-of select="$location" /></xsl:attribute><xsl:value-of select="$location" /></a>
						</span></xsl:if>&#160;
					</td>
					<td class="field" valign="top" width="15%">
						<xsl:value-of select="observationValue" />&#160;
					</td>
				</tr>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="createRequest">
					<xsl:with-param name="requestName" select="'Observation Request'" />
					<xsl:with-param name="hoverText" select="observationCode/@displayName"/>
					<xsl:with-param name="hoverCode" select="observationCode/@code"/>
					<xsl:with-param name="hoverCodeSystemName" select="observationCode/@codeSystemName"/>
					<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
					<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
					<xsl:with-param name="title" select="''" />
					<xsl:with-param name="textAlternative" select="textAlternative" />
					<xsl:with-param name="location" select="''" />
					<xsl:with-param name="targetDisplayName" select="target/@displayName" />
					<xsl:with-param name="targetCode" select="target/@code" />
					<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
		
	<xsl:template match="ProcedureRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Procedure Request'" />
			<xsl:with-param name="hoverText" select="procedureTypeCode/@displayName"/>
			<xsl:with-param name="hoverCode" select="procedureTypeCode/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="procedureTypeCode/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="SubstanceAdministrationRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Substance Administration Request'" />
			<xsl:with-param name="hoverText" select="codedProductName/@displayName"/>
			<xsl:with-param name="hoverCode" select="codedProductName/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="codedProductName/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="EventRequest|eventRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Event Request'" />
			<xsl:with-param name="hoverText" select="eventType/@displayName"/>
			<xsl:with-param name="hoverCode" select="eventType/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="eventType/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="SupplyRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Supply Request'" />
			<xsl:with-param name="hoverText" select="supplyTypeCode/@displayName"/>
			<xsl:with-param name="hoverCode" select="supplyTypeCode/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="supplyTypeCode/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="ClinicalStateRequest">
		<xsl:call-template name="createRequest">
			<xsl:with-param name="requestName" select="'Clinical State Request'" />
			<xsl:with-param name="hoverText" select="stateCode/@displayName"/>
			<xsl:with-param name="hoverCode" select="stateCode/@code"/>
			<xsl:with-param name="hoverCodeSystemName" select="stateCode/@codeSystemName"/>
			<xsl:with-param name="ancestorCode" select="ancestor::*/operator/@code" />
			<xsl:with-param name="selectionCriteria" select="selectionCriteria" />
			<xsl:with-param name="title" select="''" />
			<xsl:with-param name="textAlternative" select="textAlternative" />
			<xsl:with-param name="location" select="''" />
			<xsl:with-param name="targetDisplayName" select="target/@displayName" />
			<xsl:with-param name="targetCode" select="target/@code" />
			<xsl:with-param name="targetCodeSystemName" select="target/@codeSystemName" />
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="LogicalConditionSet">
		<xsl:param name="logicalConditionSetCode" select="operator/@code" />
			<xsl:if test="title!=''">
				<span class="fieldTitle">
					<b><xsl:value-of select="title"/></b>
				</span>
			</xsl:if>
			<br />
			<xsl:if test="ApplicableScenario">
				<xsl:apply-templates select="ApplicableScenario" />
			</xsl:if>
	</xsl:template>
	
	<xsl:template match="LogicalConditionSingle">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="2%">&#160;</td>
				<td colspan="3" class="field">
					<xsl:choose>
						<xsl:when test="description!=''">
							<xsl:value-of select="description"/>
						</xsl:when>
						<xsl:when test="title!='' and ancestor::*/ancestor::*/recommendation!=''">
							<xsl:value-of select="title"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="title"/>
						</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</table>
	</xsl:template>
		
	<xsl:template match="Definition">
		<table width="100%" cellpadding="2" cellspacing="2" cols="2">
			<tr>
				<td class="field">
					<b>Display Name</b>
				</td>
				<td class="field">
					<b>Code</b>
				</td>
			</tr>
			<xsl:for-each select="definition">
				<tr>
					<td class="field" valign="top">
						<xsl:value-of select="code/@displayName"/>
					</td>
					<td class="field">
						<xsl:value-of select="code/@code"/>&#160;(<xsl:value-of select="code/@codeSystemName"/>:&#160;<xsl:value-of select="code/@codeSystemVersion"/>)
			</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<xsl:template name="createRequest">
		<xsl:param name="requestName" />
		<xsl:param name="hoverText"/>
		<xsl:param name="hoverCode"/>
		<xsl:param name="hoverCodeSystemName"/>
		<xsl:param name="ancestorCode" />
		<xsl:param name="selectionCriteria" />
		<xsl:param name="title" />
		<xsl:param name="textAlternative" />
		<xsl:param name="location" />
		<xsl:param name="targetDisplayName"  />
		<xsl:param name="targetCode" />
		<xsl:param name="targetCodeSystemName" />
		<xsl:choose>
			<xsl:when test="$selectionCriteria='false'" />
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$ancestorCode='XOR'">&#160;&#160;&#160;
						<xsl:element name="input">
							<xsl:attribute name="type">radio</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="$hoverText"/></xsl:attribute>
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="input">
							<xsl:attribute name="type">checkbox</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of select="$hoverText"/></xsl:attribute>
							<xsl:if test="$selectionCriteria='recommended'">
								<xsl:attribute name="checked">checked</xsl:attribute>
							</xsl:if>
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		<span class="field" title="{$hoverText}">
			<b><xsl:value-of select="$requestName" />:&#160;&#160;</b>
			<xsl:if test="$title!=''"><xsl:value-of select="$title" />.&#160;</xsl:if>
			<xsl:choose>
				<xsl:when test="$textAlternative!=''">
					<xsl:value-of select="$textAlternative"/>&#160;
					<xsl:if test="$hoverText!=''">-&#160;<xsl:value-of select="$hoverText" />&#160;</xsl:if>
				</xsl:when>
				<xsl:when test="$title!=''">
					<xsl:value-of select="$title"/>&#160;
					<xsl:if test="$hoverText!=''">-&#160;<xsl:value-of select="$hoverText" />&#160;</xsl:if>
				</xsl:when>
				<xsl:otherwise><xsl:value-of select="$hoverText"/></xsl:otherwise>
			</xsl:choose>
			<xsl:if test="$targetDisplayName!=''">
				&#160;&#160;<i><b>Target:</b></i>&#160;<xsl:value-of select="$targetDisplayName" />&#160;(<xsl:value-of select="$targetCodeSystemName" />:&#160;&#160;<xsl:value-of select="$targetCode" />)
			</xsl:if>
			<xsl:if test="$location!=''">&#160;&#160;&#160;&#160;&#160;&#160;&#160;<span class="fieldLink">
				<a target='_blank'><xsl:attribute name="href"><xsl:value-of select="$location" /></xsl:attribute><xsl:value-of select="$location" /></a>
			</span></xsl:if>
			<xsl:if test="$selectionCriteria='textbox'">&#160;
				<xsl:element name="input">
					<xsl:attribute name="type">text</xsl:attribute>
					<xsl:attribute name="size">1</xsl:attribute>
				</xsl:element>
			</xsl:if>
		</span>
	</xsl:template>
	
	<xsl:template name="createColumns">
		<tr>
			<td width="50%" class="field" align="left">
				<b>Type</b>
			</td>
			<td width="50%" class="field" align="left">
				<b>Status</b>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="createColumnsForMeds">
		<tr>
			<td width="50%" class="fieldHeader" align="center">Product Class</td>
			<td width="50%" class="fieldHeader" align="center">Status of Medication</td>
		</tr>
	</xsl:template>
	<xsl:template name="createColumnsForLabs">
		<xsl:call-template name="createColumns" />
	</xsl:template>
	
	<xsl:template name="createTypeStatusTable">
		<xsl:param  name="displayNameType" />
		<xsl:param  name="codeSystemNameType" />
		<xsl:param  name="codeType" />
		<xsl:param  name="displayNameStatus"  />
		<xsl:param  name="codeSystemNameStatus"  />
		<xsl:param  name="codeStatus" />
		<tr>
			<td class="field" valign="top">
				<xsl:value-of select="$displayNameType"/>&#160;(
				<xsl:value-of select="$codeSystemNameType"/>:&#160;<xsl:value-of select="$codeType"/>)</td>
			<td class="field" valign="top">
				<xsl:if test="$codeSystemNameStatus!=''">
					<xsl:value-of select="$displayNameStatus"/>&#160;&#160;(
					<xsl:value-of select="$codeSystemNameStatus"/>:&#160;&#160;
					<xsl:value-of select="$codeStatus"/>)
				</xsl:if>&#160;
			</td>
		</tr>
  </xsl:template>
		
</xsl:stylesheet>
