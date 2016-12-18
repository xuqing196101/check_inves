package bss.model.ppms.ext;

import java.util.List;

import bss.model.ppms.Project;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;

public class ProjectExt extends Project {
    //包id
	private String packageId;
	//包名
	private String packageName;
	//增加项目进度
	private ReviewProgress reviewProgress;
	
	private List<PackageExpert> packageExperts;

    public ReviewProgress getReviewProgress() {
        return reviewProgress;
    }

    public void setReviewProgress(ReviewProgress reviewProgress) {
        this.reviewProgress = reviewProgress;
    }
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

  public List<PackageExpert> getPackageExperts() {
    return packageExperts;
  }

  public void setPackageExperts(List<PackageExpert> packageExperts) {
    this.packageExperts = packageExperts;
  }
	
}
