import gensim, logging, word2vec, gzip, bz2
from gensim.models import word2vec
import math

def sim2phrase(str1,str2,model):
	str1Array=[]
	str2Array=[]
	str1Array=str1.split()
	str2Array=str2.split()
	sum1=0.000000000
	temp=0.000000000
	pr1=""
	pr2=""
	#binnng
	for i in range(len(str1Array)):
		for j in range(len(str2Array)):
			try:
				if model.similarity(str1Array[i], str2Array[j])>temp:
					temp=model.similarity(str1Array[i], str2Array[j])
					pr1=str1Array[i]
					pr2=str2Array[j]
			except:
				pass
		#print "word1=",pr1,"word2=",pr2,"sim=",temp
		sum1+=temp
		temp=0.000000000
	#print "phrase1=",str1," phrase2=",str2, " and sum=",sum1
	return (sum1)

def wordCount(str1):
	str1Array=[]
	str1Array=str1.split()
	return (len(str1Array))

def main():
	file = open('/home/farbod/Documents/University/Courses/Teamproject/Finalforreport/Runningthecodehere/forPython.txt', 'r')
	lineCounter=0	
	for line in file:
		lineCounter+=1
	file.close()
	model = word2vec.Word2Vec.load_word2vec_format('/home/farbod/Downloads/GoogleNews-vectors-negative300.bin', binary=True)
	check=[]
	check2=[]
	show=[]
	cluster=[]
	#cluster=[[0],[1],[2],[3],[4],[5],[6],[7],[8],[9]]
	#phrases=["","","","","","","","","",""]
	phrases=[]
	phrasesClean=[]
	chkappend=0
	clusterappend=0
	replaceCnt=0
	file = open('/home/farbod/Documents/University/Courses/Teamproject/Finalforreport/Runningthecodehere/forPython.txt', 'r')
	for line in file:
		phrasesClean.append(line)
		phrases.append("")
		for h in range(0,len(phrasesClean[replaceCnt])):
			if phrasesClean[replaceCnt][h]!="]" and phrasesClean[replaceCnt][h]!="[" and phrasesClean[replaceCnt][h]!="-" and phrasesClean[replaceCnt][h]!="0" and phrasesClean[replaceCnt][h]!="1" and phrasesClean[replaceCnt][h]!="2" and phrasesClean[replaceCnt][h]!="3" and phrasesClean[replaceCnt][h]!="4" and phrasesClean[replaceCnt][h]!="5" and phrasesClean[replaceCnt][h]!="6" and phrasesClean[replaceCnt][h]!="7" and phrasesClean[replaceCnt][h]!="8" and phrasesClean[replaceCnt][h]!="9":
				phrases[replaceCnt]=phrases[replaceCnt]+phrasesClean[replaceCnt][h]
		replaceCnt+=1
		check.append(chkappend)
		check2.append(chkappend)
		show.append(chkappend)
		cluster.append([clusterappend])
		clusterappend+=1
	file.close()
	for n in range(len(phrases)):
		cnt,cnt1,cnt2,num,myClt=0,0,0,0,-1
		avg,high,temp=0.00000000,0.00000000,0.00000000
		lowPhrase=1000
		info=[]
		for g in range(lineCounter):
			info.append([[0.00000000],[0.00000000],[0],[0.00000000]])
		#info=[[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]],[[0.00000000],[0.00000000],[0],[0.00000000]]]
		for i in range(len(phrases)):
			if check[cnt1]==0:
				for j in range(len(phrases)):
					if cnt2!=cnt1:
						cnt=0
						for z in range(len(cluster[cnt2])):
							if (sim2phrase(phrases[cnt1],phrases[cluster[cnt2][cnt]],model)/wordCount(phrases[cnt1]))<lowPhrase:
								myClt=cluster[cnt2][cnt]
								lowPhrase=(sim2phrase(phrases[cnt1],phrases[cluster[cnt2][cnt]],model)/wordCount(phrases[cnt1]))
							cnt+=1
						temp=(sim2phrase(phrases[cnt1],phrases[myClt],model)/wordCount(phrases[cnt1]))
						#print "phrase1=",cnt1," phrase2=",myClt," sim=",temp
						if temp>high:
							high=temp
							num=cnt2
						avg+=temp
						cnt=0
						lowPhrase=1000
						myClt=-1
					cnt2+=1
				#if vice versa is lower than avg
				avg=avg/(len(phrases)-1)
				if (sim2phrase(phrases[num],phrases[cnt1],model)/wordCount(phrases[num]))<avg:
					check[cnt1]=1
					show[cnt1]=1
					#print "n=",n," and cnt1=",cnt1," and num=",num
					info[cnt1][0]=0.0
					info[cnt1][1]=0.0
					info[cnt1][2]=0
					info[cnt1][3]=0.0
					avg, num, high=0,0,0
					cnt1+=1
					cnt2=0
				else:
					info[cnt1][0]=avg
					info[cnt1][1]=high
					info[cnt1][2]=num
					info[cnt1][3]=(sim2phrase(phrases[num],phrases[cnt1],model)/wordCount(phrases[num]))
					avg, num, high=0,0,0
					cnt1+=1
					cnt2=0
				
			else:
				cnt1+=1
		
		merg1,merg2,cnt=0,0,0
		tempHigh=0.00000000
		for i in range(len(info)):
			if check[cnt]==0:
				if info[cnt][1]>tempHigh:
					tempHigh=info[cnt][1]
					merg1=cnt
					merg2=info[cnt][2]
				cnt+=1
			else:
				cnt+=1
		if isinstance(merg1, int)==True and isinstance(merg2, int)==True:
			if merg1==0 and merg2==0:
				merg1=0
			else:
				tnt1,breakPoint=0,0
				for x in range(len(cluster)):
					if check2[x]==0:
						tnt2=0
						for y in range(len(cluster[tnt1])):
							if cluster[tnt1][tnt2]==merg2 and merg1!=tnt1:
								cluster[tnt1].append(merg1)
								check2[merg1]=1
								show[merg1]=0
								show[tnt1]=1
								breakPoint=1
								print "n=",n,"merg1=",merg1,"merg2=",cluster[tnt1][tnt2]
							tnt2+=1
							if breakPoint==1:
								break
					tnt1+=1
					if breakPoint==1:
						break
				check[merg2]=1
				check[merg1]=1
	cnt=0
	for i in range(len(show)):
		if show[cnt]==1:
			print cluster[cnt]
		cnt+=1
main()
