#include <iostream>
#include<fstream>
using namespace std;

int main()
{
	ifstream cin("input.txt"); //把输入流定位到aaa.txt文件
	int nums[1000] = { 0 }; //数组
	int m, n;//行列
	cin >> m >> n;
	for (int i = 0; i < n; i++) {
		cin >> nums[i];
	}
	cout << "数组的行为" << m << "数组的列为" << n << endl;;
	cout << "数组的数据如下" << endl;
		for (int i = 0; i < n; i++) {
			cout << nums[i] << "    ";
		}
	int pre = 0, maxAns = nums[0];
	for (const auto& x : nums) {
		pre = max(pre + x, x);
		maxAns = max(maxAns, pre);
	}
	cout << endl << "最大子数组的和为" << maxAns;
}

