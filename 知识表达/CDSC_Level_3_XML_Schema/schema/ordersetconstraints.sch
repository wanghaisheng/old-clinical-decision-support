<?xml version="1.0" encoding="UTF-8"?>
<!--
     Copyright © 2011 Brigham and Women's Hospital, Inc. All rights reserved.

     Support Acknowledgment: This material is derived from work supported
     under a contract with the Agency for Healthcare Research and Quality (AHRQ),
     an agency of the Department of Health and Human Services (Contract No. HHSA290200810010).
     AHRQ supports health services research that will improve the quality of health
     care and promote evidence-based decision making.

     THIS SOFTWARE IS PROVIDED BY THE BRIGHAM AND WOMEN'S HOSPITAL, INC.
     "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
     TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
     LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
     DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
     SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
     CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
     OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
     USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 -->
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron"
	queryBinding="xslt">
	<sch:title>Modality-specific constraints for reminders</sch:title>

	<sch:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema" />
	<sch:ns prefix="fn" uri="http://www.w3.org/2005/xpath-functions" />

	<sch:pattern name="Order Set Action Count">
		<sch:rule context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Order Set']/action">
			<sch:assert
				test="count(../presentationGroup/action[.=current()/@actionId])=1">
				Action
				<value-of select="@actionId" />
				must be included in one and only one presentation group
			</sch:assert>

		</sch:rule>
	</sch:pattern>

	<!-- Add rules about limiting the types of behaviors -->
	<sch:pattern name="Order Set Allowed Behaviors">
		<sch:rule context="CDSKnowledgeDocument/knowledgeModule[metadata/implementation/knowledgeType='Order Set']/behavior[appliesTo/knowledgeElementType='KnowledgeModule']">
			<!-- This is a hack to identify the behavior refers to an order set - 
				the assumption is that the full path of the knowledge element is specified 
				in the appliesTo element with the knowledgeElementId attribute as a predicate 
				e.g., /CDSKnowledgeBase/knowledgeElement[@knowledgeElementId='23'] Also, 
				the behaviors of subelements of the order set will use the full-path of those 
				subelements written in a similar way. -->
			<sch:assert
				test="constraintType = 'ApplicableScenario' or constraintType = 'Trigger'">
				Order sets only can have ApplicableScenario or Trigger as behavior types</sch:assert>
		</sch:rule>
	</sch:pattern>
<!--
	<sch:pattern name="No coverage in metadata">
		<sch:rule context="CDSKnowledgeBase/metadata">
			<sch:assert test="count(coverage)=0">
			Order set must include some coverage elements
			</sch:assert>
		</sch:rule>
	</sch:pattern>
-->
</sch:schema>
